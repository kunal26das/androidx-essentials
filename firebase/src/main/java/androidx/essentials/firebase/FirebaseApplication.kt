package androidx.essentials.firebase

import android.content.SharedPreferences
import androidx.essentials.core.Application
import com.google.firebase.iid.FirebaseInstanceId

abstract class FirebaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initFirebase()
        single { initSharedPreferences() }
    }

    private fun initFirebase() {
        single { Firebase.getInstance() }
        single { FirebaseInstanceId.getInstance() }
    }

    protected abstract fun initSharedPreferences(): SharedPreferences

}