require core-image-opencpn.bb

DESCRIPTION = "Weston with mesa-driver-i965 and ssh server"

IMAGE_FEATURES += "ssh-server-dropbear"

IMAGE_INSTALL_append = " gdb strace"
