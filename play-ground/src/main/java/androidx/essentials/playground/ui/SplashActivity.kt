package androidx.essentials.playground.ui

import android.app.Activity

class SplashActivity : Activity() {

    override fun onResume() {
        super.onResume()
        PlayGroundActivity.start(this)?.finish()
    }

}