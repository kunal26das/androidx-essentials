package androidx.essentials.playground.backdrop

import android.content.SharedPreferences
import androidx.essentials.network.mutableLiveDataOf
import androidx.essentials.playground.Preference
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BackdropViewModel @Inject constructor(
    sharedPreferences: SharedPreferences
) : ViewModel() {

    val isHideable by sharedPreferences.mutableLiveDataOf<Boolean>(Preference.is_hideable)
    val isDraggable by sharedPreferences.mutableLiveDataOf<Boolean>(Preference.is_draggable)
    val skipCollapsed by sharedPreferences.mutableLiveDataOf<Boolean>(Preference.skip_collapsed)

}