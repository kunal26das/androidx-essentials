package androidx.essentials.playground.ui.text_input

import androidx.essentials.core.lifecycle.observer.ViewModel
import androidx.essentials.playground.Preference
import androidx.essentials.preferences.SharedPreferences
import androidx.essentials.preferences.SharedPreferences.Companion.mutableLiveData

class TextInputViewModel : ViewModel(), SharedPreferences {

    val textInput by mutableLiveData<String>(Preference.TEXT_INPUT)
    val isEditable by mutableLiveData<Boolean>(Preference.IS_EDITABLE)
    val isMandatory by mutableLiveData<Boolean>(Preference.IS_MANDATORY)
    val isHintEnabled by mutableLiveData<Boolean>(Preference.IS_HINT_ENABLED)

}