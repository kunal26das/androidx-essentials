package androidx.essentials.playground.ui.backdrop

import androidx.essentials.core.lifecycle.observer.ViewModel
import androidx.essentials.playground.Preference
import androidx.essentials.preferences.SharedPreferences
import androidx.essentials.preferences.SharedPreferences.Companion.mutableLiveData

class BackdropViewModel : ViewModel(), SharedPreferences {

    val isHideable by mutableLiveData<Boolean>(Preference.IS_HIDEABLE)
    val isDraggable by mutableLiveData<Boolean>(Preference.IS_DRAGGABLE)
    val skipCollapsed by mutableLiveData<Boolean>(Preference.SKIP_COLLAPSED)

}