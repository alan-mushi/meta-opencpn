require recipes-graphics/images/core-image-weston.bb

DESCRIPTION = "Weston with mesa-driver-i965"

IMAGE_INSTALL_append = " mesa-driver-i965 man-pages sudo connman config-image-opencpn"

DISTRO_FEATURES = "alsa bluetooth ext2 ipsec keyboard pci pcmcia systemd usbgadget usbhost wayland wifi"

MACHINE_FEATURES = "acpi alsa apm bluetooth ext2 keyboard pci pcmcia screen touchscreen usbgadget usbhost wifi"
