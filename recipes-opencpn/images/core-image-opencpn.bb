require recipes-graphics/images/core-image-weston.bb

DESCRIPTION = "Weston with mesa-driver-i965"

IMAGE_INSTALL_append = " mesa-driver-i965 man-pages"
