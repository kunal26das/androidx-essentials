package androidx.essentials.playground.location

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.Tasks

interface LocationService {
    suspend fun getLastLocation(
        fusedLocationProviderClient: FusedLocationProviderClient
    ) = Tasks.await(fusedLocationProviderClient.lastLocation)
}