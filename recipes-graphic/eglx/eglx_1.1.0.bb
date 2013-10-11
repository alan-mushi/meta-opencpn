SUMMARY = "X11/GLX wrapper for Wayland/EGL"
DESCRIPTION = "EGLX wraps basic X11/GLX calls, used by popular Unix OpenGL applications, to EGL/Wayland ones. \
Coupled with the jwzGLES wrapper, it permits to run legacy OpenGL applications on a Wayland compositor, \
eventually using hardware acceleration."
HOMEPAGE = "https://github.com/Tarnyko/EGLX"
SECTION = "wayland"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=27818cd7fd83877a8e3ef82b82798ef4"

RDEPENDS_${PN} = "wayland mesa gtk+3"
S = "${WORKDIR}/git"

SRC_URI = "git://github.com/Tarnyko/EGLX"

SRCREV_default_pn-${PN} = "b82af86a8a0daf3408fb7a6e7b07b02b01476626"

do_compile() {
	$CC -c -fPIC EGLX.c -o EGLX.o -DWITH_GTK `pkg-config --cflags --libs wayland-client wayland-egl egl gtk+-3.0` -I${STAGING_INCDIR}
	$CC -shared -Wl,-soname,libEGLX.so -o libEGLX.so EGLX.o -lc
	rm EGLX.o

	cd ${S}/examples/
	ln -fs ../libEGLX.so ../EGLX*.h .
	$CC glxgears.c jwzgles.c -o glxgears `pkg-config --cflags --libs wayland-client wayland-egl egl glesv1_cm gtk+-3.0` libEGLX.so -DHAVE_JWZGLES -DGL_VERSION_ES_CM_1_0 -lm -I${STAGING_INCDIR} -I${S} -I${STAGING_LIBDIR} -L${STAGING_LIBDIR}
}

do_install() {
	install -d ${D}${libdir} ${D}${includedir} ${D}${bindir}
	cp ${S}/libEGLX.so ${D}${libdir}
	cp ${S}/EGLX*.h ${D}${includedir}
	cp ${S}/examples/glxgears ${D}${bindir}
}

FILES_${PN} = "${libdir}/libEGLX.so ${bindir}/glxgears"
FILES_${PN}-dev = "${includedir}/EGLX*.h"
