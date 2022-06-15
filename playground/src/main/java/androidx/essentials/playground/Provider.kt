package androidx.essentials.playground

import android.content.Context
import androidx.essentials.network.Network
import androidx.essentials.network.local.SharedPreferences
import androidx.essentials.playground.network.NetworkRequest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object Provider {

    @Provides
    fun getNetworkRequest() = NetworkRequest.getInstance()

    @Provides
    fun getNetwork(
        context: Context,
        sharedPreferences: SharedPreferences,
        networkRequest: android.net.NetworkRequest,
    ) = Network(context, sharedPreferences, networkRequest)

    @Provides
    fun getSharedPreferences(
        @ApplicationContext context: Context
    ) = SharedPreferences(context)

}