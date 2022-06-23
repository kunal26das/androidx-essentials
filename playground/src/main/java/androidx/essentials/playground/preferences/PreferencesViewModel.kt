package androidx.essentials.playground.preferences

import androidx.essentials.network.local.Preferences
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    preferences: Preferences
) : ViewModel() {

    val int by preferences.mutableLiveDataOf<Int>(KEY_INT)
    val long by preferences.mutableLiveDataOf<Long>(KEY_LONG)
    val float by preferences.mutableLiveDataOf<Float>(KEY_FLOAT)
    val string by preferences.mutableLiveDataOf<String>(KEY_STRING)
    val boolean by preferences.mutableLiveDataOf<Boolean>(KEY_BOOLEAN)

    companion object {
        const val KEY_INT = "int"
        const val KEY_LONG = "long"
        const val KEY_FLOAT = "float"
        const val KEY_STRING = "string"
        const val KEY_BOOLEAN = "boolean"
    }

}