package androidx.essentials.playground.chips

import androidx.essentials.network.local.SharedPreferences.mutableLiveData
import androidx.essentials.playground.Preference
import androidx.essentials.playground.home.Library
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChipsViewModel : ViewModel() {

    val selection = MutableLiveData<Set<String>>()
    val libraries get() = Library.values().map { it.name }.toSet()
    val isCheckable by mutableLiveData<Boolean>(Preference.is_checkable)
    val isMandatory by mutableLiveData<Boolean>(Preference.is_mandatory)
    val isSingleSelection by mutableLiveData<Boolean>(Preference.is_single_selection)

}