package androidx.essentials.playground.ui

import android.app.Activity
import android.content.Intent

class SplashActivity : Activity() {

    override fun onResume() {
        super.onResume()
        Intent(this, PlayGroundActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }
}