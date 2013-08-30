LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=eae820e88f2d80d26676bfda11107cce"

SRC_URI = "${SOURCEFORGE_MIRROR}/project/${BPN}/${BPN}/${PV}/${BPN}-${PV}.tar.gz"

SRC_URI[md5sum] = "b53813d155df1a45371abc8f781e6d88"
SRC_URI[sha256sum] = "9f3c65411ad3932027e3c3e765337d89be2f9cf5ee9a204da80e92a8c2d76ca5"

S = "${WORKDIR}/${BPN}-${PV}"

inherit autotools

RDEPENDS_${PN} = "python"
DEPENDS = "python-native"

EXTRA_OEMAKE = "PYTHON_CPPFLAGS=\"-I${STAGING_INCDIR_NATIVE}/python2.7\""
