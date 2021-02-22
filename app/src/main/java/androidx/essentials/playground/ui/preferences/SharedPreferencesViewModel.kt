package androidx.essentials.playground.ui.preferences

import androidx.essentials.core.lifecycle.observer.ViewModel
import androidx.essentials.core.preference.SharedPreferences
import androidx.essentials.core.preference.SharedPreferences.Companion.mutableLiveData

class SharedPreferencesViewModel : ViewModel(), SharedPreferences {

    val _int by mutableLiveData<Int>(KEY_INT)
    val _long by mutableLiveData<Long>(KEY_LONG)
    val _float by mutableLiveData<Float>(KEY_FLOAT)
    val _string by mutableLiveData<String>(KEY_STRING)
    val _boolean by mutableLiveData<Boolean>(KEY_BOOLEAN)

    companion object {
        private const val KEY_INT = "int"
        private const val KEY_LONG = "long"
        private const val KEY_FLOAT = "float"
        private const val KEY_STRING = "string"
        private const val KEY_BOOLEAN = "boolean"
    }

}