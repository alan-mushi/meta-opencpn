#!/bin/bash

echo "export XDG_RUNTIME_DIR=/var/run/user/opencpn/" >> /etc/profile
echo "export export GDK_BACKEND=wayland" >> /etc/profile

patch -p 1 < weston.mod
patch -p 1 < sudoers.mod

rm weston.mod sudoers.mod first-boot.sh

echo "Press <enter> to reboot"
read
/sbin/reboot
