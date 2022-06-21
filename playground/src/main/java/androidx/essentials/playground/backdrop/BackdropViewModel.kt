package androidx.essentials.playground.backdrop

import androidx.essentials.network.local.Preferences
import androidx.essentials.playground.Preference
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BackdropViewModel @Inject constructor(
    preferences: Preferences
) : ViewModel() {

    val isHideable by preferences.mutableLiveData<Boolean>(Preference.is_hideable)
    val isDraggable by preferences.mutableLiveData<Boolean>(Preference.is_draggable)
    val skipCollapsed by preferences.mutableLiveData<Boolean>(Preference.skip_collapsed)

}