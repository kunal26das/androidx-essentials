package androidx.essentials.playground

import android.widget.Toast
import androidx.essentials.core.Activity
import androidx.essentials.events.Events

class PlayGroundActivity : Activity() {

    override val layout = R.layout.activity_play_ground
    override val viewModel by viewModel<PlayGroundViewModel>()

    override fun initObservers() {
        Events.subscribe(String::class.java) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

}