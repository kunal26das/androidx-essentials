package androidx.essentials.playground

import android.content.Context
import androidx.essentials.network.local.SharedPreferences
import androidx.essentials.playground.firebase.Firebase
import androidx.essentials.playground.network.Network
import androidx.essentials.playground.network.NetworkRequest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    fun getFirebase(
        @ApplicationContext context: Context
    ) = Firebase(context)

    @Provides
    fun getNetworkRequest() = NetworkRequest.getInstance()

    @Provides
    fun getNetwork(
        @ApplicationContext context: Context,
        networkRequest: android.net.NetworkRequest,
    ) = Network(context, networkRequest)

    @Provides
    fun getSharedPreferences(
        @ApplicationContext context: Context
    ) = SharedPreferences(context)

}