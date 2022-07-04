package androidx.essentials.playground.datetime

import android.content.SharedPreferences
import androidx.essentials.network.mutableLiveDataOf
import androidx.essentials.playground.Constant
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DateTimePickerViewModel @Inject constructor(
    sharedPreferences: SharedPreferences
) : ViewModel(), Constant {

    val endDate by sharedPreferences.mutableLiveDataOf<Long>(KEY_END_DATE)
    val startDate by sharedPreferences.mutableLiveDataOf<Long>(KEY_START_DATE)

}