DESCRIPTION = "OpenCPN is a free software (GPLv2) project to create a concise chartplotter and navigation software for use as an underway or planning tool. OpenCPN is developed by a team of active sailors using real world conditions for program testing and refinement."
HOMEPAGE = "http://opencpn.org/ocpn/"
LICENSE = "GPLv2+"

LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
PR = "r0"

SRC_URI = "https://github.com/OpenCPN/OpenCPN/archive/${PN}-${PV}.tar.gz"

SRC_URI[md5sum] = "67b09415a14f32a8f023f39802df375e"
SRC_URI[sha256sum] = "a65638fe160c2f87b57a93949a64554b198a8a0b2eaeb33e7e49201cb5103f2b"

inherit cmake

S = "${WORKDIR}/OpenCPN-${PN}-${PV}"

DEPENDS = "wxwidgets mesa gtk+3 gpsd "
RDEPENDS_${PN} = "wxwidgets gtk+3 libxcalibrate"

EXTRA_OECMAKE = "-DCMAKE_SKIP_RPATH=ON \
                 -DCMAKE_INSTALL_PREFIX=${prefix} \
                 -DCFLAGS='-O2 -march=native' \
                 "
##########################
# can't find wx/*.h path #
##########################
EXTRA_OEMAKE = "-I${sysroot}/${includedir}/wx-2.9/"

#FILES_${PN} = "${bindir}/lame"
