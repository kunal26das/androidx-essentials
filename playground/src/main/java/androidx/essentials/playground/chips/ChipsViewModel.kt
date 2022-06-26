package androidx.essentials.playground.chips

import androidx.essentials.network.Preferences
import androidx.essentials.playground.Feature
import androidx.essentials.playground.Preference
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChipsViewModel @Inject constructor(
    preferences: Preferences
) : ViewModel() {

    val selection = MutableLiveData<Set<String>>()
    val features get() = Feature.values().map { it.name }.toSet()
    val isCheckable by preferences.mutableLiveDataOf<Boolean>(Preference.is_checkable)
    val isMandatory by preferences.mutableLiveDataOf<Boolean>(Preference.is_mandatory)
    val isSingleSelection by preferences.mutableLiveDataOf<Boolean>(Preference.is_single_selection)

}