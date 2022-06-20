package androidx.essentials.playground

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.net.ConnectivityManager
import androidx.essentials.playground.firebase.FirebaseService
import androidx.essentials.playground.network.NetworkRequest
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    fun getFirebaseService() = FirebaseService.getInstance()

    @Provides
    fun getNetworkRequest() = NetworkRequest().getInstance()

    @Provides
    fun getFusedLocationProviderClient(
        @ApplicationContext context: Context
    ) = LocationServices.getFusedLocationProviderClient(context)

    @Provides
    fun getConnectivityManager(
        @ApplicationContext context: Context
    ) = context.getSystemService(ConnectivityManager::class.java)!!

    @Provides
    fun getSharedPreferences(
        @ApplicationContext context: Context
    ) = context.getSharedPreferences(context.packageName, MODE_PRIVATE)!!

}