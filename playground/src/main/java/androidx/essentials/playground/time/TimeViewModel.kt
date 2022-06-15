package androidx.essentials.playground.time

import androidx.essentials.network.local.SharedPreferences.mutableLiveData
import androidx.essentials.playground.Preference
import androidx.lifecycle.ViewModel

class TimeViewModel : ViewModel() {

    val endTime by mutableLiveData<Long>(Preference.end_time)
    val startTime by mutableLiveData<Long>(Preference.start_time)
    val isEditable by mutableLiveData<Boolean>(Preference.is_editable)
    val isMandatory by mutableLiveData<Boolean>(Preference.is_mandatory)
    val isHintEnabled by mutableLiveData<Boolean>(Preference.is_hint_enabled)

}