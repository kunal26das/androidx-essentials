package androidx.essentials.playground.preferences

import androidx.essentials.network.local.Preferences
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    preferences: Preferences
) : ViewModel() {

    val int by preferences.mutableLiveData<Int>(KEY_INT)
    val long by preferences.mutableLiveData<Long>(KEY_LONG)
    val float by preferences.mutableLiveData<Float>(KEY_FLOAT)
    val string by preferences.mutableLiveData<String>(KEY_STRING)
    val boolean by preferences.mutableLiveData<Boolean>(KEY_BOOLEAN)

    val array = arrayOf(
        KEY_INT to int,
        KEY_LONG to long,
        KEY_FLOAT to float,
        KEY_STRING to string,
    )

    companion object {
        const val KEY_INT = "int"
        const val KEY_LONG = "long"
        const val KEY_FLOAT = "float"
        const val KEY_STRING = "string"
        const val KEY_BOOLEAN = "boolean"
    }

}