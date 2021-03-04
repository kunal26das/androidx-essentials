package androidx.essentials.playground.ui.home

import androidx.essentials.core.lifecycle.owner.NavigationActivity
import androidx.essentials.core.preference.SharedPreferences
import androidx.essentials.core.preference.SharedPreferences.get
import androidx.essentials.core.preference.SharedPreferences.put
import androidx.essentials.playground.Preference
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.ActivityHomeBinding
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.common.collect.HashBiMap

class HomeActivity : NavigationActivity() {

    override val layout = R.layout.activity_home
    override val navHostFragment = R.id.playGroundNavigation
    override val appBarConfiguration by lazy {
        AppBarConfiguration(navController.graph, binding.drawerLayout)
    }

    override val viewModel by viewModel<HomeViewModel>()
    override val binding by dataBinding<ActivityHomeBinding>()

    override fun initNavigation() {
        super.initNavigation()
        binding.navigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.appBarLayout.toolbar.setTitleTextAppearance(this, R.style.ToolbarTitle)
        SharedPreferences.get<String>(Preference.DESTINATION)?.let {
            destination[it]?.let { navigate(it) }
        }
    }

    override fun initObservers() {
        super.initObservers()
        subscribe<Int> { navigate(it) }
    }

    override fun navigate(destination: Int) {
        super.navigate(destination)
        SharedPreferences.put(Pair(Preference.DESTINATION, destination))
    }

    companion object {
        private val destination = HashBiMap.create<String, Int>().apply {
            this["shared_preferences"] = R.id.sharedPreferences
            this["backdrop"] = R.id.backdrop
            this["firebase"] = R.id.firebase
            this["location"] = R.id.location
            this["network"] = R.id.network
            this["date"] = R.id.date
            this["home"] = R.id.home
        }
    }

}
