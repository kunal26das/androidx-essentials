package androidx.essentials.playground.time

import androidx.essentials.network.local.Preferences
import androidx.essentials.playground.Preference
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TimeViewModel @Inject constructor(
    preferences: Preferences
) : ViewModel() {

    val endTime by preferences.mutableLiveData<Long>(Preference.end_time)
    val startTime by preferences.mutableLiveData<Long>(Preference.start_time)
    val isEditable by preferences.mutableLiveData<Boolean>(Preference.is_editable)
    val isMandatory by preferences.mutableLiveData<Boolean>(Preference.is_mandatory)
    val isHintEnabled by preferences.mutableLiveData<Boolean>(Preference.is_hint_enabled)

}