--- /etc/init.d/weston	2013-07-11 10:50:50.129331222 +0200
+++ /etc/init.d/weston	2013-07-11 11:00:39.160664340 +0200
@@ -27,14 +27,13 @@
   start)
         . /etc/profile
 
-        # This is all a nasty hack
-        if test -z "$XDG_RUNTIME_DIR"; then
-                export XDG_RUNTIME_DIR=/var/run/user/root
-                mkdir --parents $XDG_RUNTIME_DIR
-                chmod 0700 $XDG_RUNTIME_DIR
-        fi
+	if test ! -d $XDG_RUNTIME_DIR; then
+		mkdir --parents $XDG_RUNTIME_DIR
+		chown -R opencpn:gopencpn $XDG_RUNTIME_DIR
+		chmod 0700 $XDG_RUNTIME_DIR
+	fi
 
-        weston
+	weston-launch -u opencpn
   ;;
 
   stop)
