package androidx.essentials.playground.ui.splash

import androidx.essentials.core.lifecycle.owner.Activity
import androidx.essentials.playground.ui.home.HomeActivity

class SplashActivity : Activity() {

    override fun onResume() {
        super.onResume()
        start<HomeActivity>()?.finish()
    }

}