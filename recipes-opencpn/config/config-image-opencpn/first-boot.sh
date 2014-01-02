#!/bin/bash

echo "export GDK_BACKEND=wayland" >> /etc/profile
echo "export USER=root" >> /etc/profile
echo "export HOME=/home/$USER" >> /etc/profile

rm first-boot.sh

echo "Press <enter> to reboot"
read
/sbin/reboot
