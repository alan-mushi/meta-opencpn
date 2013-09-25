SUMMARY = "The GTK+ 3 port of the wxWidgets library"
DESCRIPTION = "wxBase is a collection of C++ classes providing basic data structures (strings, \
lists, arrays), portable wrappers around many OS-specific funstions (file \
operations, time/date manipulations, threads, processes, sockets, shared \
library loading) as well as other utility classes (streams, archive and \
compression). wxBase currently supports Win32, most Unix variants (Linux, \
FreeBSD, Solaris, HP-UX) and MacOS X (Carbon and Mach-0)."
SECTION = "graphic"
LICENSE = "wxWindows"
LIC_FILES_CHKSUM = "file://docs/licence.txt;md5=18346072db6eb834b6edbd2cdc4f109b"
PR = "r0"

SRC_URI = "svn://svn.wxwidgets.org/svn/wx/wxWidgets/tags/;module=WX_2_9_5;protocol=http \
           file://tiff_m4_acinclude_remove_AX_CHECK_GL.patch \
           file://ax_check_gl.m4 \
           file://keysim_gtk_prevent_assert_error.patch \
           "

# for 'ac_raf_func_which_getservbyname_r.m4'
SRC_URI[sha256sum] = "0099a0818673ccfea006fc1bbce98693e000d1385721b3a04d27cbbe16b043ff"
# for 'gtk.m4'
SRC_URI[sha256sum] = "4d1af176791f63e35b879b470b2d391e30ef7135a37c47571122df8134ca3114"

inherit binconfig autotools

S = "${WORKDIR}/WX_2_9_5"
SRCREV_default_pn-${PN} = "74573"

RDEPENDS_${PN} = "gtk+3"

DEPENDS = "cppunit libsdl gtk+ gtk+3 autoconf-archive libglu bakefile"

PACKAGES = "${PN} ${PN}-dev ${PN}-dbg"

# Otherwise the .m4 files in build/aclocal can't be found.
#EXTRA_AUTORECONF = "-I ${S}/build/aclocal -I ${S}/aclocal-copy"
#EXTRA_OEMAKE = "samples"

EXTRA_OECONF = "--with-gtk=3 \
                --enable-utf8 \
                --enable-stl \
                --disable-display \
                --disable-mediactrl \
                --disable-uiactionsim \
                --disable-webview \
                --disable-gtktest \
                --disable-sdltest \
                --disable-rpath"

PACKAGECONFIG = "${@base_contains('DISTRO_FEATURES', 'x11', 'x11', '', d)}"
PACKAGECONFIG = "${@base_contains('DISTRO_FEATURES', 'opengl', 'opengl', '', d)}"
PACKAGECONFIG = "${@base_contains('DISTRO_FEATURES', 'wayland', 'wayland', '', d)}"
PACKAGECONFIG[x11] = "--with-x, --with-x=no, "
PACKAGECONFIG[opengl] = "--with-opengl, --with-opengl=no , virtual/libgl"
PACKAGECONFIG[wayland] = "--with-x=no, , "

cp_macro () {
        cp ${WORKDIR}/ax_check_gl.m4 ${S}/src/tiff/m4/
}

# Moves the macro at the appropriate location.
do_patch_append () {
    bb.build.exec_func('cp_macro', d)
}

# We're obliged to overrides this because any call to autoheader
# will result in "heavy failures".
autotools_do_configure () {
	${S}/autogen.sh

        cd ${S}/src/tiff/
        sed -i "5s/^autoheader/#autoheader/" ${S}/src/tiff/autogen.sh
        ${S}/src/tiff/autogen.sh

	if [ -e ${S}/configure ]; then
		cd ${S}
		oe_runconf
	fi
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

do_install_append () {
	for i in $(find ${S}/demos/ ${S}/samples/ -type f -executable | egrep -v "\.[^/]+$") ; do
		cp $i ${D}${bindir}
	done
}

INSANE_SKIP_${PN} = "dev-so"

FILES_${PN} = "${bindir}/* ${libdir}/wx/* ${libdir}/libwx*"
FILES_${PN}-dev = " ${datadir}/bakefile/* ${datadir}/aclocal/* ${prefix}/src/* ${includedir}/*"
FILES_${PN}-dbg = "${libdir}/.debug/libwx* ${bindir}/.debug/*"
