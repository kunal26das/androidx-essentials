package androidx.essentials.playground.ui.splash

import androidx.appcompat.app.AppCompatActivity
import androidx.essentials.core.lifecycle.owner.Activity.Companion.start
import androidx.essentials.playground.ui.home.HomeActivity

class SplashActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
        start<HomeActivity>()?.finish()
    }

}