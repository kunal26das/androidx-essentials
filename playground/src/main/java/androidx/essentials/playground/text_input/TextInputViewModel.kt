package androidx.essentials.playground.text_input

import androidx.essentials.playground.Preference
import androidx.essentials.preferences.SharedPreferences
import androidx.essentials.preferences.SharedPreferences.Companion.mutableLiveData
import androidx.lifecycle.ViewModel

class TextInputViewModel : ViewModel(), SharedPreferences {

    val textInput by mutableLiveData<String>(Preference.TEXT_INPUT)
    val isEditable by mutableLiveData<Boolean>(Preference.IS_EDITABLE)
    val isMandatory by mutableLiveData<Boolean>(Preference.IS_MANDATORY)
    val isHintEnabled by mutableLiveData<Boolean>(Preference.IS_HINT_ENABLED)

}