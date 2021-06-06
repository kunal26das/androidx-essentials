package androidx.essentials.playground.backdrop

import androidx.essentials.playground.Preference
import androidx.essentials.preferences.SharedPreferences.mutableLiveData
import androidx.lifecycle.ViewModel

class BackdropViewModel : ViewModel() {

    val isHideable by mutableLiveData<Boolean>(Preference.IS_HIDEABLE)
    val isDraggable by mutableLiveData<Boolean>(Preference.IS_DRAGGABLE)
    val skipCollapsed by mutableLiveData<Boolean>(Preference.SKIP_COLLAPSED)

}