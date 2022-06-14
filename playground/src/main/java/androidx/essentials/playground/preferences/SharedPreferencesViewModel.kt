package androidx.essentials.playground.preferences

import androidx.essentials.network.SharedPreferences.mutableLiveData
import androidx.essentials.playground.Preference
import androidx.lifecycle.ViewModel

class SharedPreferencesViewModel : ViewModel() {

    val _int by mutableLiveData<Int>(Preference.INT)
    val _long by mutableLiveData<Long>(Preference.LONG)
    val _float by mutableLiveData<Float>(Preference.FLOAT)
    val _string by mutableLiveData<String>(Preference.STRING)
    val _boolean by mutableLiveData<Boolean>(Preference.BOOLEAN)

}