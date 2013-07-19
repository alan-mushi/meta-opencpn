LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=eae820e88f2d80d26676bfda11107cce"

SRC_URI = "http://downloads.sourceforge.net/project/bakefile/bakefile/0.2.9/bakefile-0.2.9.tar.gz"

SRC_URI[md5sum] = "b53813d155df1a45371abc8f781e6d88"

S = "${WORKDIR}/${PN}-${PV}"

inherit autotools

EXTRA_OECONF = "--prefix=${prefix} \
                --libdir=${libdir}"
