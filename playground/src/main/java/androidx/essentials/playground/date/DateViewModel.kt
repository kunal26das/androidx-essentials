package androidx.essentials.playground.date

import android.content.SharedPreferences
import androidx.essentials.network.mutableLiveDataOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DateViewModel @Inject constructor(
    sharedPreferences: SharedPreferences
) : ViewModel() {

    val endDate by sharedPreferences.mutableLiveDataOf<Long>(KEY_END_DATE)
    val startDate by sharedPreferences.mutableLiveDataOf<Long>(KEY_START_DATE)

    companion object {
        private const val KEY_END_DATE = "end_date"
        private const val KEY_START_DATE = "start_date"
    }

}