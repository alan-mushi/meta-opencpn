FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://weston.ini;md5=84008718bc5453ce0b9889e0752a2db3"

do_install_append() {
        for i in ${D}/${sysconfdir}/skel ${D}/home/root ; do
                install -d $i/.config
                install -m755 ${WORKDIR}/weston.ini $i/.config/
        done
}

FILES_${PN} += " ${sysconfdir}/skel/.config/weston.ini /home/root/.config/weston.ini"
