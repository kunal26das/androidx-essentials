package androidx.essentials.playground.backdrop

import android.content.SharedPreferences
import androidx.essentials.network.mutableLiveDataOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BackdropViewModel @Inject constructor(
    sharedPreferences: SharedPreferences
) : ViewModel() {

    val isHideable by sharedPreferences.mutableLiveDataOf<Boolean>(KEY_HIDEABLE)
    val isDraggable by sharedPreferences.mutableLiveDataOf<Boolean>(KEY_DRAGGABLE)
    val skipCollapsed by sharedPreferences.mutableLiveDataOf<Boolean>(KEY_SKIP_COLLAPSED)

    companion object {
        private const val KEY_HIDEABLE = "hideable"
        private const val KEY_DRAGGABLE = "draggable"
        private const val KEY_SKIP_COLLAPSED = "skip_collapsed"
    }

}