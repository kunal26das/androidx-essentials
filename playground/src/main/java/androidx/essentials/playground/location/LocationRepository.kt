package androidx.essentials.playground.location

import androidx.essentials.network.Repository
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val locationServiceImpl: LocationServiceImpl
) : Repository() {

    suspend fun getLastLocation() = try {
        locationServiceImpl.getLastLocation()
    } catch (e: Throwable) {
        null
    }

}
