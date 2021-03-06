package de.spover.spover

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.provider.Settings
import androidx.core.content.ContextCompat
import android.text.TextUtils

class PermissionManager(private val context: Context) {
    companion object {
        private var TAG = PermissionManager::class.java.simpleName
    }

    fun canDrawOverlays(): Boolean {
        return Settings.canDrawOverlays(context)
    }

    fun canReadNotifications(): Boolean {
        val pkgName = context.packageName
        val flat = Settings.Secure.getString(context.contentResolver,
                "enabled_notification_listeners")
        if (!TextUtils.isEmpty(flat)) {
            // was simpler in java...
            val names = flat.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (i in names.indices) {
                val cn = ComponentName.unflattenFromString(names[i])
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.packageName)) {
                        return true
                    }
                }
            }
        }
        return false
    }

    fun canAccessLocation(): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
    }
}