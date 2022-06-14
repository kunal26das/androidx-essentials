package androidx.essentials.playground.date

import androidx.essentials.network.SharedPreferences.mutableLiveData
import androidx.essentials.playground.Preference
import androidx.lifecycle.ViewModel

class DateViewModel : ViewModel() {

    val endDate by mutableLiveData<Long>(Preference.END_DATE)
    val startDate by mutableLiveData<Long>(Preference.START_DATE)
    val isEditable by mutableLiveData<Boolean>(Preference.IS_EDITABLE)
    val isMandatory by mutableLiveData<Boolean>(Preference.IS_MANDATORY)
    val isHintEnabled by mutableLiveData<Boolean>(Preference.IS_HINT_ENABLED)

}