SUMMARY = "Add the user opencpn to the image"
DESCRIPTION = "This is a modified version of meta-skeleton/recipes-skeleton/useradd/useradd-example.bb"
SECTION = "user"
PR = "r1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = "file://weston.ini \
           file://BackGround.png \
           file://sudoers.mod \
           file://first-boot.sh \
           "

S = "${WORKDIR}"

PACKAGES = "${PN}"

PROVIDES = "config-image-opencpn"

inherit useradd

# You must set USERADD_PACKAGES when you inherit useradd. This
# lists which output packages will include the user/group
# creation code.
USERADD_PACKAGES = "${PN}"

# You must also set USERADD_PARAM and/or GROUPADD_PARAM when
# you inherit useradd.

# USERADD_PARAM specifies command line options to pass to the
# useradd command.
USERADD_PARAM_${PN} = "-d /home/opencpn -g 880 -G audio,video,sudo,weston-launch -r -s /bin/bash -p cVI4BSxOSVDoM opencpn"

# GROUPADD_PARAM works the same way, which you set to the options
# you'd normally pass to the groupadd command. This will create
# group gopencpn and weston-launch:
GROUPADD_PARAM_${PN} = "-g 880 gopencpn; weston-launch"

do_install () {
	install -d -m 755 ${D}/home/opencpn/.config

	install -p -m 644 weston.ini ${D}/home/opencpn/.config/
	install -p -m 644 BackGround.png ${D}/home/opencpn/

        install -p -m 755 first-boot.sh ${D}/
        install -p -m 644 sudoers.mod ${D}/

	# The new user and group are created before the do_install
	# step, so you are now free to make use of them:
	chown -R opencpn ${D}/home/opencpn
	chgrp -R gopencpn ${D}/home/opencpn
}

FILES_${PN} = "/home/opencpn/BackGround.png /home/opencpn/.config/weston.ini /sudoers.mod /first-boot.sh"

# Prevents do_package failures with:
# debugsources.list: No such file or directory:
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
