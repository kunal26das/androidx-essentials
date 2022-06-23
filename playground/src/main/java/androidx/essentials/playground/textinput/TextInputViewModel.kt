package androidx.essentials.playground.textinput

import androidx.essentials.network.local.Preferences
import androidx.essentials.playground.Preference
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TextInputViewModel @Inject constructor(
    preferences: Preferences
) : ViewModel() {

    val textInput by preferences.mutableLiveDataOf<String>(Preference.text_input)
    val isEditable by preferences.mutableLiveDataOf<Boolean>(Preference.is_editable)
    val isMandatory by preferences.mutableLiveDataOf<Boolean>(Preference.is_mandatory)
    val isHintEnabled by preferences.mutableLiveDataOf<Boolean>(Preference.is_hint_enabled)

}