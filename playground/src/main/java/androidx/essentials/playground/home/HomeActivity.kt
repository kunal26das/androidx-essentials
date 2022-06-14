package androidx.essentials.playground.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContract
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.ActivityHomeBinding
import androidx.essentials.ui.NavigationActivity
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
        setSupportActionBar(binding.toolbar)
        binding.navigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
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
