package androidx.essentials.playground

import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.updateMargins
import androidx.essentials.core.Activity
import androidx.essentials.core.Resources.statusBarHeight
import androidx.essentials.events.Events
import kotlinx.android.synthetic.main.activity_play_ground.*

class PlayGroundActivity : Activity() {

    override val layout = R.layout.activity_play_ground
    override val viewModel by viewModel<PlayGroundViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        (appBarLayout.layoutParams as ViewGroup.MarginLayoutParams).apply {
            updateMargins(top = statusBarHeight)
        }
        setSupportActionBar(appBarLayout.toolbar)
    }

    override fun initObservers() {
        Events.subscribe(String::class.java) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

}
