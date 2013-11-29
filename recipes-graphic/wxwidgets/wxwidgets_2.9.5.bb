require wxwidgets.inc

PR = "r0"

SRC_URI = "svn://svn.wxwidgets.org/svn/wx/wxWidgets/tags/;module=WX_2_9_5;protocol=http \
           file://tiff_m4_acinclude_remove_AX_CHECK_GL.patch \
           file://ax_check_gl.m4 \
           file://keysim_gtk_prevent_assert_error.patch \
	   file://wxwidgets295-EGLX.patch;md5=b82dcbc275b9cf8ce5fb26f7b7b32137 \
           "

# for 'ac_raf_func_which_getservbyname_r.m4'
SRC_URI[sha256sum] = "0099a0818673ccfea006fc1bbce98693e000d1385721b3a04d27cbbe16b043ff"
# for 'gtk.m4'
SRC_URI[sha256sum] = "4d1af176791f63e35b879b470b2d391e30ef7135a37c47571122df8134ca3114"

inherit binconfig autotools

S = "${WORKDIR}/WX_2_9_5"
SRCREV_default_pn-${PN} = "74573"

cp_macro () {
        cp ${WORKDIR}/ax_check_gl.m4 ${S}/src/tiff/m4/
	sed -i -e "41s/^LIBS = @LIBS@$/LIBS = @LIBS@ -lEGLX -ljwzgles \`pkg-config --cflags --libs egl wayland-egl\`/" ${S}/Makefile.in
}

# Moves the macro at the appropriate location.
do_patch_append () {
    bb.build.exec_func('cp_macro', d)
}

do_compile_append () {
	# Building the examples
	for i in ${S}/samples ${S}/demos ; do
		cd $i && make ${PARALLEL_MAKE}
	done

	# The wx-config file in ${S} is actully a "proxy script" so let's overrides it with the final script.
	# Isolate the path of the actual script.
	if [ -f ${S}/wx-config.old ] ; then
		wx_config_path=$(sed -n 38p ${S}/wx-config.old |sed "s/\$this_exec_prefix\///g" | cut -d \" -f 2)
	else
		wx_config_path=$(sed -n 38p ${S}/wx-config |sed "s/\$this_exec_prefix\///g" | cut -d \" -f 2)
		mv ${S}/wx-config ${S}/wx-config.old
	fi
	cp -f ${S}/$wx_config_path ${S}/wx-config
}
