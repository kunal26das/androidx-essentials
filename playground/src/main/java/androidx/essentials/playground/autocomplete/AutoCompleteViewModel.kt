package androidx.essentials.playground.autocomplete

import androidx.essentials.network.Preferences
import androidx.essentials.playground.Feature
import androidx.essentials.playground.Preference
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AutoCompleteViewModel @Inject constructor(
    preferences: Preferences
) : ViewModel() {

    val libraries get() = Feature.values()
    val autoComplete = MutableLiveData<Feature>()
    val filter by preferences.mutableLiveDataOf<Boolean>(Preference.filter)
    val keyboard by preferences.mutableLiveDataOf<Boolean>(Preference.keyboard)
    val isEditable by preferences.mutableLiveDataOf<Boolean>(Preference.is_editable)
    val isMandatory by preferences.mutableLiveDataOf<Boolean>(Preference.is_mandatory)
    val isHintEnabled by preferences.mutableLiveDataOf<Boolean>(Preference.is_hint_enabled)

}