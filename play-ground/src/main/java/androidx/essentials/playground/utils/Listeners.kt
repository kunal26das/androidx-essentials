package androidx.essentials.playground.utils

import androidx.essentials.location.LocationProvider
import androidx.essentials.network.NetworkCallback

interface Listeners : NetworkCallback.OnNetworkStateChangeListener,
    LocationProvider.OnLocationChangeListener