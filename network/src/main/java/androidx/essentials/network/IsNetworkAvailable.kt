package androidx.essentials.network

import androidx.lifecycle.MutableLiveData

class IsNetworkAvailable private constructor() {
    internal companion object : MutableLiveData<Boolean>()
}