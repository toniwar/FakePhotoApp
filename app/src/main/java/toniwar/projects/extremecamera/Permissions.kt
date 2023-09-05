package toniwar.projects.extremecamera

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

object Permissions {

    val REQUIRED_PERMISSIONS =
        mutableListOf(android.Manifest.permission.CAMERA).apply {
            if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.P)
                add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }.toTypedArray()

    fun hasPermissions(context: Context) = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }
}