--- /etc/sudoers	2013-07-11 11:11:16.076207072 +0200
+++ /etc/sudoers	2013-07-11 11:16:44.663497475 +0200
@@ -40,7 +40,7 @@
 ## Run X applications through sudo; HOME is used to find the
 ## .Xauthority file.  Note that other programs use HOME to find   
 ## configuration files and this may lead to privilege escalation!
-# Defaults env_keep += "HOME"
+Defaults env_keep += "HOME XDG_RUNTIME_DIR GDK_BACKEND"
 ##
 ## X11 resource path settings
 # Defaults env_keep += "XAPPLRESDIR XFILESEARCHPATH XUSERFILESEARCHPATH"
@@ -78,7 +78,7 @@
 # %wheel ALL=(ALL) NOPASSWD: ALL
 
 ## Uncomment to allow members of group sudo to execute any command
-# %sudo	ALL=(ALL) ALL
+%sudo ALL=(ALL) ALL
 
 ## Uncomment to allow any user to run sudo if they know the password
 ## of the user they are running the command as (root by default).
