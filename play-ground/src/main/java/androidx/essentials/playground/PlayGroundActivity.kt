package androidx.essentials.playground

import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.updateMargins
import androidx.essentials.core.Activity
import androidx.essentials.core.Resources.dp
import androidx.essentials.events.Events
import androidx.essentials.extensions.Coroutines.default
import androidx.essentials.extensions.View.addOnGlobalLayoutListener
import kotlinx.android.synthetic.main.activity_play_ground.*

class PlayGroundActivity : Activity() {

    override val layout = R.layout.activity_play_ground
    override val viewModel by viewModel<PlayGroundViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAppBarLayout()
        supportFragmentManager
    }

    private fun initAppBarLayout() {
        setSupportActionBar(appBarLayout.toolbar)
        appBarLayout.addOnGlobalLayoutListener {
            viewModel.appBarLayoutHeight.value = it.height
        }
        (appBarLayout.layoutParams as ViewGroup.MarginLayoutParams).default {
            updateMargins(top = 8.dp, bottom = 8.dp)
        }
    }

    override fun initObservers() {
        Events.subscribe(String::class.java) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

}
