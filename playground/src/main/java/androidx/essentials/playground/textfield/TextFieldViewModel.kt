package androidx.essentials.playground.textfield

import android.content.SharedPreferences
import androidx.essentials.network.mutableLiveDataOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TextFieldViewModel @Inject constructor(
    sharedPreferences: SharedPreferences
) : ViewModel() {

    val text by sharedPreferences.mutableLiveDataOf<String>(KEY_TEXT)
    val label by sharedPreferences.mutableLiveDataOf<String>(KEY_LABEL)
    val style by sharedPreferences.mutableLiveDataOf<Boolean>(KEY_STYLE)
    val readOnly by sharedPreferences.mutableLiveDataOf<Boolean>(KEY_READ_ONLY)
    val mandatory by sharedPreferences.mutableLiveDataOf<Boolean>(KEY_MANDATORY)
    val placeholder by sharedPreferences.mutableLiveDataOf<String>(KEY_PLACEHOLDER)

    companion object {
        private const val KEY_TEXT = "text"
        private const val KEY_LABEL = "label"
        private const val KEY_STYLE = "style"
        private const val KEY_MANDATORY = "mandatory"
        private const val KEY_READ_ONLY = "read_only"
        private const val KEY_PLACEHOLDER = "placeholder"
    }

}