package androidx.essentials.playground.backdrop

import com.google.android.material.bottomsheet.BottomSheetBehavior

fun interface StateChangeListener {
    fun onStateChange(@BottomSheetBehavior.State newState: Int)
}