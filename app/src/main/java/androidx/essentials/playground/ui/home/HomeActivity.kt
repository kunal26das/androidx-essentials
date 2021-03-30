package androidx.essentials.playground.ui.home

import androidx.essentials.core.lifecycle.owner.NavigationActivity
import androidx.essentials.events.Events.subscribe
import androidx.essentials.playground.Preference
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.ActivityHomeBinding
import androidx.essentials.preferences.SharedPreferences
import androidx.essentials.preferences.SharedPreferences.Companion.get
import androidx.essentials.preferences.SharedPreferences.Companion.put
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

class HomeActivity : NavigationActivity(), SharedPreferences {

    override val layout = R.layout.activity_home
    override val navHostFragmentId = R.id.playGroundNavigation
    override val appBarConfiguration by lazy {
        AppBarConfiguration(navController.graph, binding.drawerLayout)
    }
    override val binding by dataBinding<ActivityHomeBinding>()

    override fun initNavigation() {
        super.initNavigation()
        binding.navigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.appBarLayout.toolbar.setTitleTextAppearance(this, R.style.ToolbarTitle)
        get<Int>(Preference.DESTINATION)?.let { navigate(it) }
    }

    override fun initObservers() {
        super.initObservers()
        subscribe<Int> {
            navigate(it)
            put(Pair(Preference.DESTINATION, it))
        }
    }

}
