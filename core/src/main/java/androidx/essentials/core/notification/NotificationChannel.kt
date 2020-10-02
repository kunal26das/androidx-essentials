package androidx.essentials.core.notification

import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.O)
data class NotificationChannel(
    val id: Int,
    val name: String
)
