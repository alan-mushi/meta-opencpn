SUMMARY = "X11/GLX wrapper for Wayland/EGL"
DESCRIPTION = "EGLX wraps basic X11/GLX calls, used by popular Unix OpenGL applications, to EGL/Wayland ones. \
Coupled with the jwzGLES wrapper, it permits to run legacy OpenGL applications on a Wayland compositor, \
eventually using hardware acceleration."
HOMEPAGE = "https://github.com/Tarnyko/EGLX"
SECTION = "wayland"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=27818cd7fd83877a8e3ef82b82798ef4"

DEPENDS = "virtual/egl gtk+3"
RDEPENDS_${PN} = "wayland gtk+3"
S = "${WORKDIR}/git"

SRC_URI = "git://github.com/Tarnyko/EGLX"

SRCREV_default_pn-${PN} = "b82af86a8a0daf3408fb7a6e7b07b02b01476626"

do_compile() {
	$CC -c -fPIC EGLX.c -o EGLX.o -DWITH_GTK `pkg-config --cflags --libs wayland-client wayland-egl egl gtk+-3.0` -I${STAGING_INCDIR}
	$CC -shared -Wl,-soname,libEGLX.so -o libEGLX.so EGLX.o -lc
	rm EGLX.o

	cd ${S}/examples/
	ln -fs ../libEGLX.so ../EGLX*.h .
	$CC -c -fPIC jwzgles.c -o jwzgles.o -DHAVE_JWZGLES -DGL_VERSION_ES_CM_1_0 -I${STAGING_INCDIR}
	$CC -shared -Wl,-soname,jwzgles.so -o jwzgles.so jwzgles.o -lc
	$CC glxgears.c -o glxgears `pkg-config --cflags --libs wayland-client wayland-egl egl glesv1_cm gtk+-3.0` jwzgles.so libEGLX.so -DHAVE_JWZGLES -lm -I${STAGING_INCDIR} -I${S} -I. -L${STAGING_LIBDIR}
	rm jwzgles.o
}

do_install() {
	install -d ${D}${libdir} ${D}${includedir} ${D}${bindir}
	cp ${S}/libEGLX.so ${S}/examples/jwzgles.so ${D}${libdir}
	ln -sf ${D}${libdir}/jwzgles.so ${D}${libdir}/libjwzgles.so
	cp ${S}/EGLX*.h ${S}/examples/jwzgles*.h ${D}${includedir}
	cp ${S}/examples/glxgears ${D}${bindir}
}

INSANE_SKIP_${PN} = "dev-so"

FILES_${PN} = "${libdir}/libEGLX.so ${libdir}/*jwzgles.so ${bindir}/glxgears"
FILES_${PN}-dev = "${includedir}/EGLX*.h ${includedir}/jwzgles*.h"
