package androidx.essentials.playground.date

import android.content.SharedPreferences
import androidx.essentials.network.mutableLiveDataOf
import androidx.essentials.playground.Preference
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DateViewModel @Inject constructor(
    sharedPreferences: SharedPreferences
) : ViewModel() {

    val endDate by sharedPreferences.mutableLiveDataOf<Long>(Preference.end_date)
    val startDate by sharedPreferences.mutableLiveDataOf<Long>(Preference.start_date)
    val isEditable by sharedPreferences.mutableLiveDataOf<Boolean>(Preference.is_editable)
    val isMandatory by sharedPreferences.mutableLiveDataOf<Boolean>(Preference.is_mandatory)
    val isHintEnabled by sharedPreferences.mutableLiveDataOf<Boolean>(Preference.is_hint_enabled)

}