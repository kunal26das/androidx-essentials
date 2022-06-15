package androidx.essentials.playground.chips

import androidx.essentials.network.local.SharedPreferences
import androidx.essentials.playground.Preference
import androidx.essentials.playground.home.Feature
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChipsViewModel @Inject constructor(
    sharedPreferences: SharedPreferences
) : ViewModel() {

    val selection = MutableLiveData<Set<String>>()
    val features get() = Feature.values().map { it.name }.toSet()
    val isCheckable by sharedPreferences.mutableLiveData<Boolean>(Preference.is_checkable)
    val isMandatory by sharedPreferences.mutableLiveData<Boolean>(Preference.is_mandatory)
    val isSingleSelection by sharedPreferences.mutableLiveData<Boolean>(Preference.is_single_selection)

}