package androidx.essentials.playground.ui.date

import androidx.essentials.core.lifecycle.observer.ViewModel
import androidx.essentials.core.preference.SharedPreferences.mutableLiveData
import androidx.essentials.playground.Preference

class DateViewModel : ViewModel() {

    val endDate by mutableLiveData<Long>(Preference.END_DATE)
    val startDate by mutableLiveData<Long>(Preference.START_DATE)
    val isEditable by mutableLiveData<Boolean>(Preference.IS_EDITABLE)
    val isMandatory by mutableLiveData<Boolean>(Preference.IS_MANDATORY)
    val isHintEnabled by mutableLiveData<Boolean>(Preference.IS_HINT_ENABLED)

}