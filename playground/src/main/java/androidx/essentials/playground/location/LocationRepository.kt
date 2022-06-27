package androidx.essentials.playground.location

import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val locationServiceImpl: LocationServiceImpl
) {

    suspend fun getLastLocation() = try {
        locationServiceImpl.getLastLocation()
    } catch (e: Throwable) {
        null
    }

}
