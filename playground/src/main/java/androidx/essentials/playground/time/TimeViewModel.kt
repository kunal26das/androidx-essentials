package androidx.essentials.playground.time

import android.content.SharedPreferences
import androidx.essentials.network.mutableLiveDataOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TimeViewModel @Inject constructor(
    sharedPreferences: SharedPreferences
) : ViewModel() {

    val endTime by sharedPreferences.mutableLiveDataOf<Long>(KEY_END_TIME)
    val startTime by sharedPreferences.mutableLiveDataOf<Long>(KEY_START_TIME)

    companion object {
        private const val KEY_END_TIME = "end_time"
        private const val KEY_START_TIME = "start_time"
    }

}