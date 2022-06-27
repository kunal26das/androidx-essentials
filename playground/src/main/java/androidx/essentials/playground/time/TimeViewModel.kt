package androidx.essentials.playground.time

import android.content.SharedPreferences
import androidx.essentials.network.mutableLiveDataOf
import androidx.essentials.playground.Preference
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TimeViewModel @Inject constructor(
    sharedPreferences: SharedPreferences
) : ViewModel() {

    val endTime by sharedPreferences.mutableLiveDataOf<Long>(Preference.end_time)
    val startTime by sharedPreferences.mutableLiveDataOf<Long>(Preference.start_time)
    val isEditable by sharedPreferences.mutableLiveDataOf<Boolean>(Preference.is_editable)
    val isMandatory by sharedPreferences.mutableLiveDataOf<Boolean>(Preference.is_mandatory)
    val isHintEnabled by sharedPreferences.mutableLiveDataOf<Boolean>(Preference.is_hint_enabled)

}