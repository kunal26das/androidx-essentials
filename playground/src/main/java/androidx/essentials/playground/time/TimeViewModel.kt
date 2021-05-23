package androidx.essentials.playground.time

import androidx.essentials.playground.Preference
import androidx.essentials.preferences.SharedPreferences
import androidx.essentials.preferences.SharedPreferences.Companion.mutableLiveData
import androidx.lifecycle.ViewModel

class TimeViewModel : ViewModel(), SharedPreferences {

    val endTime by mutableLiveData<Long>(Preference.END_TIME)
    val startTime by mutableLiveData<Long>(Preference.START_TIME)
    val isEditable by mutableLiveData<Boolean>(Preference.IS_EDITABLE)
    val isMandatory by mutableLiveData<Boolean>(Preference.IS_MANDATORY)
    val isHintEnabled by mutableLiveData<Boolean>(Preference.IS_HINT_ENABLED)

}