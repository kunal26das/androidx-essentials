package androidx.essentials.playground.chips

import android.content.SharedPreferences
import androidx.essentials.network.mutableLiveDataOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChipsViewModel @Inject constructor(
    sharedPreferences: SharedPreferences
) : ViewModel() {

    val selection by sharedPreferences.mutableLiveDataOf<MutableSet<String>>(KEY_SELECTION)

    companion object {
        private const val KEY_SELECTION = "selection"
    }

}