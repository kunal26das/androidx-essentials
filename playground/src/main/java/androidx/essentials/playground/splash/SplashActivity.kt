package androidx.essentials.playground.splash

import androidx.essentials.activity.Activity
import androidx.essentials.playground.home.HomeActivity

class SplashActivity : Activity() {

    private val homeActivity = registerForActivityResult(HomeActivity) {}

    override fun onResume() {
        super.onResume()
        homeActivity.launch(null)
        finish()
    }

}