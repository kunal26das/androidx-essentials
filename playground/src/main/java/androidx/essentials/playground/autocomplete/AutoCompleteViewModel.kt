package androidx.essentials.playground.autocomplete

import androidx.essentials.network.local.SharedPreferences.mutableLiveData
import androidx.essentials.playground.Preference
import androidx.essentials.playground.library.Library
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AutoCompleteViewModel : ViewModel() {

    val autoComplete = MutableLiveData<Library>()
    val filter by mutableLiveData<Boolean>(Preference.FILTER)
    val keyboard by mutableLiveData<Boolean>(Preference.KEYBOARD)
    val isEditable by mutableLiveData<Boolean>(Preference.IS_EDITABLE)
    val isMandatory by mutableLiveData<Boolean>(Preference.IS_MANDATORY)
    val isHintEnabled by mutableLiveData<Boolean>(Preference.IS_HINT_ENABLED)

    val libraries get() = Library.values()

}