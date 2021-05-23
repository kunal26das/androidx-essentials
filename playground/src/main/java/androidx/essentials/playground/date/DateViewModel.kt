package androidx.essentials.playground.date

import androidx.essentials.playground.Preference
import androidx.essentials.preferences.SharedPreferences
import androidx.essentials.preferences.SharedPreferences.Companion.mutableLiveData
import androidx.lifecycle.ViewModel

class DateViewModel : ViewModel(), SharedPreferences {

    val endDate by mutableLiveData<Long>(Preference.END_DATE)
    val startDate by mutableLiveData<Long>(Preference.START_DATE)
    val isEditable by mutableLiveData<Boolean>(Preference.IS_EDITABLE)
    val isMandatory by mutableLiveData<Boolean>(Preference.IS_MANDATORY)
    val isHintEnabled by mutableLiveData<Boolean>(Preference.IS_HINT_ENABLED)

}