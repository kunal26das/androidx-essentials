package androidx.essentials.view

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ContentFrameLayout
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat

abstract class ComposeActivity : AppCompatActivity(), LifecycleOwner {

    protected open val sideEffect = true
    protected open val dynamicColor = true

    protected open val darkColorScheme
        get() = darkColorScheme()

    protected open val lightColorScheme
        get() = lightColorScheme()

    private val darkTheme
        @Composable get() = isSystemInDarkTheme()

    protected open val colorScheme: ColorScheme
        @Composable get() {
            val darkTheme = darkTheme
            val colorScheme = when {
                dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                    if (darkTheme) dynamicDarkColorScheme(this)
                    else dynamicLightColorScheme(this)
                }
                darkTheme -> darkColorScheme
                else -> lightColorScheme
            }
            return colorScheme
        }

    protected open val shapes
        @Composable get() = MaterialTheme.shapes

    protected open val typography
        @Composable get() = MaterialTheme.typography

    private val content: ContentFrameLayout by lazy {
        findViewById(android.R.id.content)
    }

    fun registerForActivityResult(
        contract: ActivityResultContract<*, *>
    ) = registerForActivityResult(contract) {}

    @CallSuper
    @MainThread
    protected open fun onAttach(context: Context) = Unit

    @Composable
    private fun sideEffect() {
        val darkTheme = darkTheme
        val colorScheme = colorScheme
        val controller = WindowCompat.getInsetsController(window, this.content)
        window.navigationBarColor = colorScheme.background.toArgb()
        window.statusBarColor = colorScheme.background.toArgb()
        controller.isAppearanceLightStatusBars = !darkTheme
    }

    final override fun onCreate(savedInstanceState: Bundle?) {
        onAttach(applicationContext)
        super.onCreate(savedInstanceState)
        setContent {
            if (sideEffect) sideEffect()
            MaterialTheme(colorScheme, shapes, typography) {
                onViewCreated(content, savedInstanceState)
            }
        }
    }

    @CallSuper
    @Composable
    @MainThread
    protected open fun onViewCreated(view: View, savedInstanceState: Bundle?) = Unit

    @CallSuper
    @MainThread
    open fun onDestroyView() = Unit

    override fun onDestroy() {
        onDestroyView()
        super.onDestroy()
        onDetach()
    }

    @CallSuper
    @MainThread
    protected open fun onDetach() = Unit

    @CallSuper
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

}