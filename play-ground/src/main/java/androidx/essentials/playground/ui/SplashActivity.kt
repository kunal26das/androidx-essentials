package androidx.essentials.playground.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.essentials.core.lifecycle.owner.Activity.Companion.start

class SplashActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
        PlayGroundActivity::class.java.start(this)?.finish()
    }

}