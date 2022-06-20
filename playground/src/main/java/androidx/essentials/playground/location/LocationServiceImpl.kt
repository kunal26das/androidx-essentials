package androidx.essentials.playground.location

import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import javax.inject.Inject

class LocationServiceImpl @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : LocationService {

    suspend fun getLastLocation(): Location? {
        return getLastLocation(fusedLocationProviderClient)
    }

}