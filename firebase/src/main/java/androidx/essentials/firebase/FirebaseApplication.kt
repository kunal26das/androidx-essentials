package androidx.essentials.firebase

import android.content.SharedPreferences
import androidx.essentials.core.Application

abstract class FirebaseApplication : Application() {

    protected abstract val sharedPreferences: SharedPreferences

    override fun onCreate() {
        super.onCreate()
        single { sharedPreferences }
        single { Firebase.getInstance() }
    }

}