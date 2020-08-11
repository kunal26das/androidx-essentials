package androidx.essentials.playground

import android.content.Context
import android.os.Build
import androidx.essentials.events.Events
import androidx.essentials.firebase.FirebaseApplication
import androidx.essentials.location.LocationProvider
import androidx.essentials.network.NetworkCallback
import androidx.essentials.playground.ui.PlayGroundViewModel
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.firebase.database.FirebaseDatabase

class PlayGround : FirebaseApplication() {

    override val sharedPreferences
        get() = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                EncryptedSharedPreferences.create(
                    packageName,
                    MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                    applicationContext,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )
            }
            else -> getSharedPreferences(packageName, Context.MODE_PRIVATE)!!
        }

    override fun onCreate() {
        super.onCreate()
        initViewModels()
        initComponents()
    }

    private fun initViewModels() {
        viewModel { PlayGroundViewModel() }
    }

    private fun initComponents() {
        single { LocationProvider.getInstance(applicationContext) }
        single { NetworkCallback(applicationContext) }
        single {
            FirebaseDatabase.getInstance().apply {
                setPersistenceEnabled(true)
            }.reference
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        Events.clear()
    }

}