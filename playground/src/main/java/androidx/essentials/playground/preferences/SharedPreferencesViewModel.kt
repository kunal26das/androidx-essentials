package androidx.essentials.playground.preferences

import androidx.essentials.network.local.SharedPreferences.mutableLiveData
import androidx.essentials.playground.Preference
import androidx.lifecycle.ViewModel

class SharedPreferencesViewModel : ViewModel() {

    val _int by mutableLiveData<Int>(Preference.int)
    val _long by mutableLiveData<Long>(Preference.long)
    val _float by mutableLiveData<Float>(Preference.float)
    val _string by mutableLiveData<String>(Preference.string)
    val _boolean by mutableLiveData<Boolean>(Preference.boolean)

}