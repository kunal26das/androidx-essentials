package androidx.essentials.playground.autocomplete

import android.content.SharedPreferences
import androidx.essentials.network.mutableLiveDataOf
import androidx.essentials.playground.Feature
import androidx.essentials.playground.Preference
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AutoCompleteViewModel @Inject constructor(
    sharedPreferences: SharedPreferences
) : ViewModel() {

    val libraries get() = Feature.values()
    val autoComplete = MutableLiveData<Feature>()
    val filter by sharedPreferences.mutableLiveDataOf<Boolean>(Preference.filter)
    val keyboard by sharedPreferences.mutableLiveDataOf<Boolean>(Preference.keyboard)
    val isEditable by sharedPreferences.mutableLiveDataOf<Boolean>(Preference.is_editable)
    val isMandatory by sharedPreferences.mutableLiveDataOf<Boolean>(Preference.is_mandatory)
    val isHintEnabled by sharedPreferences.mutableLiveDataOf<Boolean>(Preference.is_hint_enabled)

}