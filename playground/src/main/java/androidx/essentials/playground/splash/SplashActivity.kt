package androidx.essentials.playground.splash

import androidx.essentials.activity.Activity
import androidx.essentials.playground.home.HomeActivity

class SplashActivity : Activity() {

    override fun onResume() {
        super.onResume()
        start<HomeActivity>()?.finish()
    }

}