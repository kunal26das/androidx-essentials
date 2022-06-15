package androidx.essentials.playground.textinput

import androidx.essentials.network.local.SharedPreferences.mutableLiveData
import androidx.essentials.playground.Preference
import androidx.lifecycle.ViewModel

class TextInputViewModel : ViewModel() {

    val textInput by mutableLiveData<String>(Preference.text_input)
    val isEditable by mutableLiveData<Boolean>(Preference.is_editable)
    val isMandatory by mutableLiveData<Boolean>(Preference.is_mandatory)
    val isHintEnabled by mutableLiveData<Boolean>(Preference.is_hint_enabled)

}