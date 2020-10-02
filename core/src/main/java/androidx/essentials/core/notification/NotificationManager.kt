package androidx.essentials.core.notification

import android.app.NotificationManager
import android.content.Context
import android.os.Build

object NotificationManager {

    private var notificationManager: NotificationManager? = null

    fun getInstance(context: Context): NotificationManager {
        if (notificationManager != null) {
            return notificationManager!!
        }
        synchronized(this) {
            notificationManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                context.getSystemService(NotificationManager::class.java)
            } else {
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            }
            return notificationManager!!
        }
    }
}
