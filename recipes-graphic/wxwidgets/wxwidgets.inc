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

inherit binconfig autotools

RDEPENDS_${PN} = "gtk+3"

DEPENDS = "cppunit libsdl gtk+ gtk+3 autoconf-archive libglu bakefile"

PACKAGES = "${PN} ${PN}-dev ${PN}-dbg"

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

PACKAGECONFIG = "${@base_contains('DISTRO_FEATURES', 'x11', 'x11', '', d)}"
#PACKAGECONFIG = "${@base_contains('DISTRO_FEATURES', 'egl', 'egl', '', d)}"
PACKAGECONFIG = "${@base_contains('DISTRO_FEATURES', 'wayland', 'wayland', '', d)}"
PACKAGECONFIG[x11] = "--with-x, --with-x=no --disable-stc, "
#PACKAGECONFIG[egl] = "--with-opengl, --with-opengl=no, virtual/egl"
PACKAGECONFIG[wayland] = "--with-x=no --disable-stc, , eglx"

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
do_install_append () {
    for i in $(find ${S}/demos/ ${S}/samples/ -type f -executable | egrep -v "\.[^/]+$") ; do
       cp $i ${D}${bindir}
    done
}

INSANE_SKIP_${PN} = "dev-so"

FILES_${PN} = "${bindir}/* ${libdir}/wx/* ${libdir}/libwx*"
FILES_${PN}-dev = " ${datadir}/bakefile/* ${datadir}/aclocal/* ${prefix}/src/* ${includedir}/*"
FILES_${PN}-dbg = "${libdir}/.debug/libwx* ${bindir}/.debug/*"
