package androidx.essentials.view

import android.os.Build
import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

sealed interface ComposeController : LifecycleOwner {

    val dynamicColor
        get() = true

    val darkColorScheme
        get() = darkColorScheme()

    val lightColorScheme
        get() = lightColorScheme()

    val colorScheme: ColorScheme
        @Composable get() {
            val darkTheme = isSystemInDarkTheme()
            val colorScheme = when {
                dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                    if (darkTheme) dynamicDarkColorScheme(getContext()!!)
                    else dynamicLightColorScheme(getContext()!!)
                }
                darkTheme -> darkColorScheme
                else -> lightColorScheme
            }
            return colorScheme
        }

    val shapes
        @Composable get() = MaterialTheme.shapes

    val typography
        @Composable get() = MaterialTheme.typography

    val lifecycleOwner
        get() = when (this) {
            is ComposeActivity -> this
            is ComposeFragment -> viewLifecycleOwner
            is ComposeDialogFragment -> viewLifecycleOwner
            is ComposeBottomSheetDialogFragment -> viewLifecycleOwner
            else -> null
        }

    private fun getContext() = when (this) {
        is ComposeActivity -> this
        is ComposeFragment -> context
        is ComposeDialogFragment -> context
        is ComposeBottomSheetDialogFragment -> context
        else -> null
    }

    @CallSuper
    @Composable
    @MainThread
    fun setContent() = Unit

}