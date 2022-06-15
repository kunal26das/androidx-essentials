package androidx.essentials.playground.date

import androidx.essentials.network.local.SharedPreferences
import androidx.essentials.playground.Preference
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DateViewModel @Inject constructor(
    sharedPreferences: SharedPreferences
) : ViewModel() {

    val endDate by sharedPreferences.mutableLiveData<Long>(Preference.end_date)
    val startDate by sharedPreferences.mutableLiveData<Long>(Preference.start_date)
    val isEditable by sharedPreferences.mutableLiveData<Boolean>(Preference.is_editable)
    val isMandatory by sharedPreferences.mutableLiveData<Boolean>(Preference.is_mandatory)
    val isHintEnabled by sharedPreferences.mutableLiveData<Boolean>(Preference.is_hint_enabled)

}