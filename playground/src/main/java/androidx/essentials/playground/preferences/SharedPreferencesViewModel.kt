package androidx.essentials.playground.preferences

import android.content.SharedPreferences
import androidx.essentials.network.mutableLiveDataOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedPreferencesViewModel @Inject constructor(
    sharedPreferences: SharedPreferences
) : ViewModel() {

    val int by sharedPreferences.mutableLiveDataOf<Int>(KEY_INT)
    val long by sharedPreferences.mutableLiveDataOf<Long>(KEY_LONG)
    val float by sharedPreferences.mutableLiveDataOf<Float>(KEY_FLOAT)
    val string by sharedPreferences.mutableLiveDataOf<String>(KEY_STRING)
    val boolean by sharedPreferences.mutableLiveDataOf<Boolean>(KEY_BOOLEAN)

    companion object {
        const val KEY_INT = "int"
        const val KEY_LONG = "long"
        const val KEY_FLOAT = "float"
        const val KEY_STRING = "string"
        const val KEY_BOOLEAN = "boolean"
    }

}