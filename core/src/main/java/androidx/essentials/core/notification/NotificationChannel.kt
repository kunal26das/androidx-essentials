package androidx.essentials.core.notification

import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import kotlinx.parcelize.Parcelize

@Parcelize
@RequiresApi(Build.VERSION_CODES.O)
data class NotificationChannel(
    val id: Int,
    val name: String
) : Parcelable
