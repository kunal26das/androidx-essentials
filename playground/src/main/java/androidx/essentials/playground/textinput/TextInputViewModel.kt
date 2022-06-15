package androidx.essentials.playground.textinput

import androidx.essentials.network.local.SharedPreferences
import androidx.essentials.playground.Preference
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TextInputViewModel @Inject constructor(
    sharedPreferences: SharedPreferences
) : ViewModel() {

    val textInput by sharedPreferences.mutableLiveData<String>(Preference.text_input)
    val isEditable by sharedPreferences.mutableLiveData<Boolean>(Preference.is_editable)
    val isMandatory by sharedPreferences.mutableLiveData<Boolean>(Preference.is_mandatory)
    val isHintEnabled by sharedPreferences.mutableLiveData<Boolean>(Preference.is_hint_enabled)

}