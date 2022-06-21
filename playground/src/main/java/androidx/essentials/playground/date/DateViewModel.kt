package androidx.essentials.playground.date

import androidx.essentials.network.local.Preferences
import androidx.essentials.playground.Preference
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DateViewModel @Inject constructor(
    preferences: Preferences
) : ViewModel() {

    val endDate by preferences.mutableLiveData<Long>(Preference.end_date)
    val startDate by preferences.mutableLiveData<Long>(Preference.start_date)
    val isEditable by preferences.mutableLiveData<Boolean>(Preference.is_editable)
    val isMandatory by preferences.mutableLiveData<Boolean>(Preference.is_mandatory)
    val isHintEnabled by preferences.mutableLiveData<Boolean>(Preference.is_hint_enabled)

}