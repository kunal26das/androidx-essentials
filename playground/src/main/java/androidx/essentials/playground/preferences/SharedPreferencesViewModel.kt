package androidx.essentials.playground.preferences

import androidx.essentials.network.local.SharedPreferences
import androidx.essentials.playground.Preference
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedPreferencesViewModel @Inject constructor(
    sharedPreferences: SharedPreferences
) : ViewModel() {

    val _int by sharedPreferences.mutableLiveData<Int>(Preference.int)
    val _long by sharedPreferences.mutableLiveData<Long>(Preference.long)
    val _float by sharedPreferences.mutableLiveData<Float>(Preference.float)
    val _string by sharedPreferences.mutableLiveData<String>(Preference.string)
    val _boolean by sharedPreferences.mutableLiveData<Boolean>(Preference.boolean)

}