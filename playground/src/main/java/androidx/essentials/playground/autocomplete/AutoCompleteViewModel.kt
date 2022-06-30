package androidx.essentials.playground.autocomplete

import android.content.SharedPreferences
import androidx.essentials.network.mutableLiveDataOf
import androidx.essentials.playground.Feature
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AutoCompleteViewModel @Inject constructor(
    sharedPreferences: SharedPreferences
) : ViewModel() {

    val libraries get() = Feature.values()
    val autoComplete = MutableLiveData<Feature>()
    val filter by sharedPreferences.mutableLiveDataOf<Boolean>(KEY_FILTER)
    val keyboard by sharedPreferences.mutableLiveDataOf<Boolean>(KEY_KEYBOARD)
    val isEditable by sharedPreferences.mutableLiveDataOf<Boolean>(KEY_EDITABLE)
    val isMandatory by sharedPreferences.mutableLiveDataOf<Boolean>(KEY_MANDATORY)
    val isHintEnabled by sharedPreferences.mutableLiveDataOf<Boolean>(KEY_HINT_ENABLED)

    companion object {
        private const val KEY_FILTER = "filter"
        private const val KEY_KEYBOARD = "keyboard"
        private const val KEY_EDITABLE = "editable"
        private const val KEY_MANDATORY = "mandatory"
        private const val KEY_HINT_ENABLED = "hint_enabled"
    }

}