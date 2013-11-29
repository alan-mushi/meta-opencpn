require wxwidgets.inc

PV = "3.0.0"

SRC_URI = "svn://svn.wxwidgets.org/svn/wx/wxWidgets/tags/;module=WX_3_0_0;protocol=http \
           file://tiff_m4_acinclude_remove_AX_CHECK_GL.patch \
           file://ax_check_gl.m4 \
	   file://wxwidgets-svn_r74946-EGLX.patch \
        "

# for 'ac_raf_func_which_getservbyname_r.m4'
SRC_URI[sha256sum] = "0099a0818673ccfea006fc1bbce98693e000d1385721b3a04d27cbbe16b043ff"
# for 'wxwidgets-svn_r74946-EGLX.patch'
SRC_URI[sha256sum] = "d943367d63462a24ec20693764d045ba8da94796de0217acbc46198e2f689896"

S = "${WORKDIR}/WX_3_0_0"
SRCREV_default_pn-${PN} = "75180"

EXTRA_OECONF = "OPENGL_LIBS='-lEGLX -ljwzgles' \
		--with-gtk=3 \
                --disable-utf8 \
                --disable-stl \
                --disable-display \
                --disable-mediactrl \
                --disable-uiactionsim \
                --disable-webview \
                --disable-gtktest \
                --disable-sdltest \
                --disable-rpath \
		--with-opengl \
		--disable-detect_sm \
		"

additionnal_patches () {
    cp ${WORKDIR}/ax_check_gl.m4 ${S}/src/tiff/m4/
    # The original Makefile.in does non-compatible things with cross-compilation, so let's use the previous version
    sed -i "16104c\	(cd \$(DESTDIR)\$(bindir) && rm -f wx-config && \$(LN_S) ../\`basename \$(libdir)\`\/wx\/config\/\$(TOOLCHAIN_FULLNAME) wx-config)" ${S}/Makefile.in
    sed -i -e "s/^LIBS = @LIBS@$/LIBS = @LIBS@ -lEGLX -ljwzgles \$(pkg-config --cflags --libs egl wayland-egl) -lGLESv1_CM/" ${S}/Makefile.in
    find ${S}/samples/ -name Makefile.in -exec sed -i "s/@LIBS@$/@LIBS@ -lEGLX -ljwzgles \$(pkg-config --cflags --libs egl wayland-egl) -lGLESv1_CM/g" {} \;
}

# Moves the macro at the appropriate location.
do_patch_append () {
    bb.build.exec_func('additionnal_patches', d)
}

do_compile_append () {
    # Building the examples
    #for i in ${S}/samples ${S}/demos ; do
    #    cd $i &&  make ${PARALLEL_MAKE}
    #done

    # The wx-config file in ${S} is actully a "proxy script" so let's overrides it with the final script.
    # Isolate the path of the actual script.
    if [ -f ${S}/wx-config.old ] ; then
        wx_config_path=$(sed -n 37p ${S}/wx-config.old |sed "s/\$this_exec_prefix\///g" | cut -d \" -f 2)
    else
        wx_config_path=$(sed -n 37p ${S}/wx-config |sed "s/\$this_exec_prefix\///g" | cut -d \" -f 2)
        mv ${S}/wx-config ${S}/wx-config.old
    fi
    cp -f ${S}/$wx_config_path ${S}/wx-config
}
