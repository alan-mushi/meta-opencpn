meta-opencpn
============

This is a project that have for goal to realize a porting of OpenCPN on Wayland with Yocto. I'm working on adding suport for opengl in wxwidgets when configured for wayland (by default opengl only works for X11 in wxwidgets).

The [wiki](https://github.com/alan-mushi/meta-opencpn/wiki) can be used as a tutorial for you to build your own version of this project.

***
Everything cannot be carried out on a layer, you will need to manually add :

* in your `[build directory]/conf/local.conf` file :
```text
MACHINE_FEATURES_append = " acpi alsa apm bluetooth ext2 keyboard pci pcmcia screen touchscreen usbgadget usbhost wifi"
```

* in the `meta-yocto/conf/distro/poky.conf` file :
```text
DISTRO_FEATURES_append = " alsa bluetooth ext2 ipsec keyboard pci pcmcia systemd usbgadget usbhost wayland wifi pam"
VIRTUAL-RUNTIME_init_manager = "systemd"
```
