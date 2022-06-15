package androidx.essentials.playground.home

import android.os.Bundle
import android.view.View
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.ActivityHomeBinding
import androidx.essentials.view.Activity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController

class HomeActivity : Activity() {

    override val layout = R.layout.activity_home
    override val binding: ActivityHomeBinding by dataBinding()

    private val navHostFragmentId = R.id.playGroundNavigation
    private val navController get() = navHostFragment.navController
    private val appBarConfiguration by lazy { AppBarConfiguration(navController.graph) }
    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(navHostFragmentId) as NavHostFragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navHostFragment
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

}
