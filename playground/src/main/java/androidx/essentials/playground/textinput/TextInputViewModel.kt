package androidx.essentials.playground.textinput

import androidx.essentials.network.local.SharedPreferences.mutableLiveData
import androidx.essentials.playground.Preference
import androidx.lifecycle.ViewModel

class TextInputViewModel : ViewModel() {

    val textInput by mutableLiveData<String>(Preference.TEXT_INPUT)
    val isEditable by mutableLiveData<Boolean>(Preference.IS_EDITABLE)
    val isMandatory by mutableLiveData<Boolean>(Preference.IS_MANDATORY)
    val isHintEnabled by mutableLiveData<Boolean>(Preference.IS_HINT_ENABLED)

}