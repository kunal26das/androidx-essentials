package androidx.essentials.playground.autocomplete

import androidx.essentials.network.local.SharedPreferences.mutableLiveData
import androidx.essentials.playground.Preference
import androidx.essentials.playground.library.Library
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AutoCompleteViewModel : ViewModel() {

    val autoComplete = MutableLiveData<Library>()
    val filter by mutableLiveData<Boolean>(Preference.filter)
    val keyboard by mutableLiveData<Boolean>(Preference.keyboard)
    val isEditable by mutableLiveData<Boolean>(Preference.is_editable)
    val isMandatory by mutableLiveData<Boolean>(Preference.is_mandatory)
    val isHintEnabled by mutableLiveData<Boolean>(Preference.is_hint_enabled)

    val libraries get() = Library.values()

}