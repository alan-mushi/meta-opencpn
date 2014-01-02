SUMMARY = "Add the user opencpn to the image"
DESCRIPTION = "This is a modified version of meta-skeleton/recipes-skeleton/useradd/useradd-example.bb"
SECTION = "user"
PR = "r1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = "file://weston.ini \
           file://BackGround.png \
           file://first-boot.sh \
           "

S = "${WORKDIR}"

PACKAGES = "${PN}"

PROVIDES = "config-image-opencpn"

do_install () {
	install -d -m 755 ${D}/home/root/.config

	install -p -m 644 weston.ini ${D}/home/root/.config/
	install -p -m 644 BackGround.png ${D}/home/root/

        install -p -m 755 first-boot.sh ${D}/
}

FILES_${PN} = "/home/root/BackGround.png /home/root/.config/weston.ini /first-boot.sh"

# Prevents do_package failures with:
# debugsources.list: No such file or directory:
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
