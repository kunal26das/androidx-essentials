package androidx.essentials.playground.backdrop

import androidx.essentials.network.local.SharedPreferences.mutableLiveData
import androidx.essentials.playground.Preference
import androidx.lifecycle.ViewModel

class BackdropViewModel : ViewModel() {

    val isHideable by mutableLiveData<Boolean>(Preference.is_hideable)
    val isDraggable by mutableLiveData<Boolean>(Preference.is_draggable)
    val skipCollapsed by mutableLiveData<Boolean>(Preference.skip_collapsed)

}