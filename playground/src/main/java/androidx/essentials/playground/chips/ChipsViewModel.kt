package androidx.essentials.playground.chips

import android.content.SharedPreferences
import androidx.essentials.network.mutableLiveDataOf
import androidx.essentials.playground.Constant
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChipsViewModel @Inject constructor(
    sharedPreferences: SharedPreferences
) : ViewModel(), Constant {

    val selections by sharedPreferences.mutableLiveDataOf<MutableSet<String>>(KEY_SELECTIONS)

}