package androidx.essentials.playground.hilt

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.datastore.preferences.preferencesDataStore
import androidx.essentials.playground.firebase.FirebaseService
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object Module {

    private val Context.dataStore by preferencesDataStore(javaClass.simpleName)

    @Provides
    fun getPreferencesDataStore(
        @ApplicationContext context: Context
    ) = context.dataStore

    @Provides
    fun getFirebaseService() = FirebaseService.INSTANCE

    @Provides
    fun getFusedLocationProviderClient(
        @ApplicationContext context: Context
    ) = LocationServices.getFusedLocationProviderClient(context)

    @Provides
    fun getSharedPreferences(
        @ApplicationContext context: Context
    ) = context.getSharedPreferences(context.packageName, MODE_PRIVATE)!!

}