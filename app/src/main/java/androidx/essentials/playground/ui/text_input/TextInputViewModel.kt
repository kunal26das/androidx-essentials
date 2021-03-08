package androidx.essentials.playground.ui.text_input

import androidx.essentials.core.lifecycle.observer.ViewModel
import androidx.essentials.core.preference.SharedPreferences.mutableLiveData
import androidx.essentials.playground.Preference

class TextInputViewModel : ViewModel() {

    val textInput by mutableLiveData<String>(Preference.TEXT_INPUT)
    val isEditable by mutableLiveData<Boolean>(Preference.IS_EDITABLE)
    val isMandatory by mutableLiveData<Boolean>(Preference.IS_MANDATORY)
    val isHintEnabled by mutableLiveData<Boolean>(Preference.IS_HINT_ENABLED)

}