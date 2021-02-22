package androidx.essentials.playground.ui.splash

import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.essentials.core.lifecycle.owner.Activity.Companion.start
import androidx.essentials.playground.ui.home.HomeActivity

class SplashActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
        HomeActivity::class.java.start(
            this, bundleOf(

            )
        )?.finish()
    }

}