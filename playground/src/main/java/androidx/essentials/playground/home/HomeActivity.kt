package androidx.essentials.playground.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContract
import androidx.essentials.activity.NavigationActivity
import androidx.essentials.events.Events.subscribe
import androidx.essentials.playground.Preference
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.ActivityHomeBinding
import androidx.essentials.preferences.SharedPreferences.put
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

class HomeActivity : NavigationActivity() {

    override val layout = R.layout.activity_home
    override val navHostFragmentId = R.id.playGroundNavigation
    override val appBarConfiguration by lazy {
        AppBarConfiguration(navController.graph, binding.drawerLayout)
    }
    override val binding by dataBinding<ActivityHomeBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
//        get<Int>(Preference.DESTINATION)?.let { navigate(it) }
    }

    override fun initObservers() {
        super.initObservers()
        subscribe<Int> {
            if (navigate(it)) {
                put(Preference.DESTINATION, it)
            }
        }
    }

    companion object : ActivityResultContract<Any?, Boolean>() {
        override fun createIntent(context: Context, input: Any?): Intent {
            return Intent(context, HomeActivity::class.java)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
            return resultCode == RESULT_OK
        }

    }

}
