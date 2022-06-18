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
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat

abstract class ComposeActivity : AppCompatActivity() {

    protected open val dynamicColor: Boolean = true

    fun registerForActivityResult(
        contract: ActivityResultContract<*, *>
    ) = registerForActivityResult(contract) {}

    private val content by lazy { findViewById<ContentFrameLayout>(android.R.id.content) }

    @CallSuper
    @MainThread
    protected open fun onAttach(context: Context) = Unit

    final override fun onCreate(savedInstanceState: Bundle?) {
        onAttach(applicationContext)
        super.onCreate(savedInstanceState)
        setContent {
            val darkTheme = isSystemInDarkTheme()
            val colorScheme = when {
                dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                    if (darkTheme) dynamicDarkColorScheme(this)
                    else dynamicLightColorScheme(this)
                }
                darkTheme -> darkColorScheme()
                else -> lightColorScheme()
            }
            SideEffect {
                val controller = WindowCompat.getInsetsController(window, this.content)
                window.navigationBarColor = colorScheme.background.toArgb()
                window.statusBarColor = colorScheme.background.toArgb()
                controller.isAppearanceLightStatusBars = !darkTheme
            }
            MaterialTheme(
                colorScheme = colorScheme,
                typography = Typography(),
                content = {
                    onViewCreated(content, savedInstanceState)
                },
            )
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