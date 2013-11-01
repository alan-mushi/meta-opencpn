DESCRIPTION = "OpenCPN is a free software (GPLv2) project to create a concise chartplotter and navigation software for use as an underway or planning tool. OpenCPN is developed by a team of active sailors using real world conditions for program testing and refinement."
HOMEPAGE = "http://opencpn.org/ocpn/"
LICENSE = "GPLv2+"

LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
PR = "r0"

SRC_URI = "https://github.com/OpenCPN/OpenCPN/archive/${PN}-${PV}.tar.gz \
           file://opencpn320-EGLX.patch;sha256=1ff528c25acbcbf94bbb10eec8c11ed8bd8c494df2d54c774eb4e106892db33d \
	   file://FindGTK3.cmake;sha256=b8aed5e001d7059ec045ff990ba9f291c468393fee22c538eabdc932b74c6a7f"

# Checksum for the archive
SRC_URI[md5sum] = "21916fad3b44cc18c09361304e6b8314"
SRC_URI[sha256sum] = "bb07ecdd4ccc2fd836c02b575848ef310deafd121f7c6015a121663d395f0e3e"

inherit cmake

S = "${WORKDIR}/OpenCPN-${PN}-${PV}"

DEPENDS = "mesa libtinyxml wxwidgets gtk+3 libxcalibrate gpsd eglx"
RDEPENDS_${PN} = "mesa libtinyxml wxwidgets gtk+3 libxcalibrate gpsd eglx"

EXTRA_OECMAKE = "-DCMAKE_SKIP_RPATH=ON \
                 -DCMAKE_INSTALL_PREFIX=${prefix} \
                 -DCFLAGS='-ggdb -march=native' \
		 -DSKIP_PLUGINS=ON \
                 "

do_configure_prepend () {
	ln -sf ${WORKDIR}/FindGTK3.cmake ${S}
}

additionnal_patches () {
	dos2unix ${S}/src/options.cpp
}

do_patch_prepend () {
    bb.build.exec_func('additionnal_patches', d)
}

FILES_${PN} = "${datadir}/* ${bindir}/opencpn"
