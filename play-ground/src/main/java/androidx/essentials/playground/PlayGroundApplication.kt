package androidx.essentials.playground

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.essentials.events.Events
import androidx.essentials.firebase.FirebaseApplication
import androidx.essentials.location.LocationProvider
import androidx.essentials.network.NetworkCallback
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class PlayGroundApplication : FirebaseApplication() {

    override fun onCreate() {
        super.onCreate()
        initViewModels()
        initComponents()
    }

    private fun initViewModels() {
        viewModel { PlayGroundViewModel() }
    }

    private fun initComponents() {
        single { NetworkCallback(applicationContext) }
        single { LocationProvider.getInstance(applicationContext) }
    }

    override fun initSharedPreferences(): SharedPreferences {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            EncryptedSharedPreferences.create(
                packageName,
                MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                applicationContext,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } else {
            getSharedPreferences(packageName, Context.MODE_PRIVATE)!!
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        Events.clear()
    }

}