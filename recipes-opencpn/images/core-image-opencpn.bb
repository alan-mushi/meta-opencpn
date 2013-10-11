require recipes-graphics/images/core-image-weston.bb

DESCRIPTION = "Weston with mesa-driver-i965"

IMAGE_INSTALL_append = " mesa-driver-i965 man man-pages sudo connman config-image-opencpn \
                         gpsd gpsd-udev libgpsd libgps gpsd-conf gpsd-gpsctl gps-utils python-pygps \
                         wxwidgets pulseaudio pulseaudio-server pulseaudio-misc links consolekit eglx"

DISTRO_FEATURES_append = " gles"
