package androidx.essentials.playground.datetime

import android.content.SharedPreferences
import androidx.essentials.network.mutableLiveDataOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DateTimePickerViewModel @Inject constructor(
    sharedPreferences: SharedPreferences
) : ViewModel() {

    val endDate by sharedPreferences.mutableLiveDataOf<Long>(KEY_END_DATE)
    val endTime by sharedPreferences.mutableLiveDataOf<Long>(KEY_END_TIME)
    val startDate by sharedPreferences.mutableLiveDataOf<Long>(KEY_START_DATE)
    val startTime by sharedPreferences.mutableLiveDataOf<Long>(KEY_START_TIME)

    companion object {
        private const val KEY_END_DATE = "end_date"
        private const val KEY_END_TIME = "end_time"
        private const val KEY_START_DATE = "start_date"
        private const val KEY_START_TIME = "start_time"
    }

}