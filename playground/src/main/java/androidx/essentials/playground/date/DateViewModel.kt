package androidx.essentials.playground.date

import androidx.essentials.network.Preferences
import androidx.essentials.playground.Preference
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DateViewModel @Inject constructor(
    preferences: Preferences
) : ViewModel() {

    val endDate by preferences.mutableLiveDataOf<Long>(Preference.end_date)
    val startDate by preferences.mutableLiveDataOf<Long>(Preference.start_date)
    val isEditable by preferences.mutableLiveDataOf<Boolean>(Preference.is_editable)
    val isMandatory by preferences.mutableLiveDataOf<Boolean>(Preference.is_mandatory)
    val isHintEnabled by preferences.mutableLiveDataOf<Boolean>(Preference.is_hint_enabled)

}