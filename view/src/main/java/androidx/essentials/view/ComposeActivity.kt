package androidx.essentials.view

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ContentFrameLayout
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.fragment.app.DialogFragment

abstract class ComposeActivity : AppCompatActivity(), ComposeController {

    protected open val sideEffect = true

    private val content: ContentFrameLayout by lazy {
        findViewById(android.R.id.content)
    }

    protected fun <I, O> registerForActivityResult(
        contract: ActivityResultContract<I, O>,
    ) = registerForActivityResult(contract) {}

    @CallSuper
    @MainThread
    protected open fun onAttach(context: Context) = Unit

    @Composable
    private fun SideEffect() {
        val colorScheme = colorScheme
        val darkTheme = isSystemInDarkTheme()
        val controller = WindowCompat.getInsetsController(window, this.content)
        window.navigationBarColor = colorScheme.background.toArgb()
        window.statusBarColor = colorScheme.background.toArgb()
        controller.isAppearanceLightStatusBars = !darkTheme
    }

    final override fun onCreate(savedInstanceState: Bundle?) {
        onAttach(applicationContext)
        super.onCreate(savedInstanceState)
        setContent {
            if (sideEffect) SideEffect()
            MaterialTheme(colorScheme, shapes, typography) {
                Content()
            }
        }
    }

    final override fun onCreate(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        super.onCreate(savedInstanceState, persistentState)
    }

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

    @Synchronized
    protected fun DialogFragment.showNow(tag: String? = null) = try {
        showNow(supportFragmentManager, tag)
        null
    } catch (e: Throwable) {
        e
    }

}