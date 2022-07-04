package androidx.essentials.playground.autocomplete

import android.content.SharedPreferences
import androidx.essentials.network.mutableLiveDataOf
import androidx.essentials.playground.Constant
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AutoCompleteViewModel @Inject constructor(
    sharedPreferences: SharedPreferences
) : ViewModel(), Constant {

    val filter by sharedPreferences.mutableLiveDataOf<Boolean>(KEY_FILTER)
    val expanded by sharedPreferences.mutableLiveDataOf<Boolean>(KEY_EXPANDED)
    val readOnly by sharedPreferences.mutableLiveDataOf<Boolean>(KEY_READ_ONLY)
    val selection by sharedPreferences.mutableLiveDataOf<String>(KEY_SELECTION)

}