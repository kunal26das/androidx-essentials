package androidx.essentials.playground.splash

import androidx.essentials.playground.home.HomeActivity
import androidx.essentials.ui.Activity

class SplashActivity : Activity() {

    private val homeActivity = registerForActivityResult(HomeActivity) {}

    override fun onResume() {
        super.onResume()
        homeActivity.launch(null)
        finish()
    }

}