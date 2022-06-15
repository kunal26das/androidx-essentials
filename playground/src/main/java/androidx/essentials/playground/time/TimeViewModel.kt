package androidx.essentials.playground.time

import androidx.essentials.network.local.SharedPreferences
import androidx.essentials.playground.Preference
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TimeViewModel @Inject constructor(
    sharedPreferences: SharedPreferences
) : ViewModel() {

    val endTime by sharedPreferences.mutableLiveData<Long>(Preference.end_time)
    val startTime by sharedPreferences.mutableLiveData<Long>(Preference.start_time)
    val isEditable by sharedPreferences.mutableLiveData<Boolean>(Preference.is_editable)
    val isMandatory by sharedPreferences.mutableLiveData<Boolean>(Preference.is_mandatory)
    val isHintEnabled by sharedPreferences.mutableLiveData<Boolean>(Preference.is_hint_enabled)

}