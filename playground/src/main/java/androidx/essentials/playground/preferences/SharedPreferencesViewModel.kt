package androidx.essentials.playground.preferences

import android.content.SharedPreferences
import androidx.essentials.network.mutableLiveDataOf
import androidx.essentials.playground.Constant
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedPreferencesViewModel @Inject constructor(
    sharedPreferences: SharedPreferences
) : ViewModel(), Constant {

    val int by sharedPreferences.mutableLiveDataOf<Int>(KEY_INT)
    val long by sharedPreferences.mutableLiveDataOf<Long>(KEY_LONG)
    val float by sharedPreferences.mutableLiveDataOf<Float>(KEY_FLOAT)
    val string by sharedPreferences.mutableLiveDataOf<String>(KEY_STRING)
    val boolean by sharedPreferences.mutableLiveDataOf<Boolean>(KEY_BOOLEAN)

}