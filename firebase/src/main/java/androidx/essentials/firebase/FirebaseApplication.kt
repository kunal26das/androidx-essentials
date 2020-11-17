package androidx.essentials.firebase

import android.content.SharedPreferences
import androidx.essentials.core.Application
import com.google.firebase.installations.FirebaseInstallations

abstract class FirebaseApplication : Application() {

    protected abstract val sharedPreferences: SharedPreferences

    override fun onCreate() {
        super.onCreate()
        initFirebase()
        single { sharedPreferences }
    }

    private fun initFirebase() {
        single { Firebase.getInstance() }
        single { FirebaseInstallations.getInstance() }
    }

}