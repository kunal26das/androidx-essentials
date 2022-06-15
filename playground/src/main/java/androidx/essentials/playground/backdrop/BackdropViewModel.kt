package androidx.essentials.playground.backdrop

import androidx.essentials.network.local.SharedPreferences
import androidx.essentials.playground.Preference
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BackdropViewModel @Inject constructor(
    sharedPreferences: SharedPreferences
) : ViewModel() {

    val isHideable by sharedPreferences.mutableLiveData<Boolean>(Preference.is_hideable)
    val isDraggable by sharedPreferences.mutableLiveData<Boolean>(Preference.is_draggable)
    val skipCollapsed by sharedPreferences.mutableLiveData<Boolean>(Preference.skip_collapsed)

}