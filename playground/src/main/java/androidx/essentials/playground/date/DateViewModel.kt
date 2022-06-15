package androidx.essentials.playground.date

import androidx.essentials.network.local.SharedPreferences.mutableLiveData
import androidx.essentials.playground.Preference
import androidx.lifecycle.ViewModel

class DateViewModel : ViewModel() {

    val endDate by mutableLiveData<Long>(Preference.end_date)
    val startDate by mutableLiveData<Long>(Preference.start_date)
    val isEditable by mutableLiveData<Boolean>(Preference.is_editable)
    val isMandatory by mutableLiveData<Boolean>(Preference.is_mandatory)
    val isHintEnabled by mutableLiveData<Boolean>(Preference.is_hint_enabled)

}