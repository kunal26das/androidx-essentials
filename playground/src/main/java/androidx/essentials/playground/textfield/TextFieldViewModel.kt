package androidx.essentials.playground.textfield

import android.content.SharedPreferences
import androidx.essentials.network.mutableLiveDataOf
import androidx.essentials.playground.Constant
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TextFieldViewModel @Inject constructor(
    sharedPreferences: SharedPreferences
) : ViewModel(), Constant {

    val text by sharedPreferences.mutableLiveDataOf<String>(KEY_TEXT)
    val style by sharedPreferences.mutableLiveDataOf<Boolean>(KEY_STYLE)
    val readOnly by sharedPreferences.mutableLiveDataOf<Boolean>(KEY_READ_ONLY)
    val mandatory by sharedPreferences.mutableLiveDataOf<Boolean>(KEY_MANDATORY)

}