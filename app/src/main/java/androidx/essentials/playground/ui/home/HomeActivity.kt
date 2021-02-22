package androidx.essentials.playground.ui.home

import androidx.annotation.IdRes
import androidx.essentials.core.injector.KoinComponent.inject
import androidx.essentials.core.lifecycle.owner.NavigationActivity
import androidx.essentials.extensions.Coroutines.default
import androidx.essentials.extensions.Coroutines.main
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.ActivityHomeBinding
import androidx.essentials.preferences.EncryptedSharedPreferences
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
    private val sharedPreferences by inject<EncryptedSharedPreferences>()

    override fun initNavigation() {
        super.initNavigation()
        binding.navigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.appBarLayout.toolbar.setTitleTextAppearance(this, R.style.ToolbarTitle)
        sharedPreferences.get<String>(KEY_DESTINATION)?.apply {
            destination[this]?.let { navigate(it) }
        }
    }

    override fun initObservers() {
        super.initObservers()
        subscribe<Int> { navigate(it) }
    }

    private fun Any.navigate(@IdRes destination: Int) {
        navController.default {
            if ((destination in graph.map { it.id })) {
                if ((currentDestination?.id != destination)) main {
                    sharedPreferences.put(Pair(KEY_DESTINATION, destination))
                    navigate(destination)
                }
            }
        }
    }

    companion object {
        private const val KEY_DESTINATION = "destination"
        private val destination = HashBiMap.create<String, Int>().apply {
            this["shared_preferences"] = R.id.sharedPreferences
            this["location"] = R.id.location
            this["firebase"] = R.id.firebase
            this["backdrop"] = R.id.backdrop
            this["network"] = R.id.network
            this["home"] = R.id.home
            this["io"] = R.id.io
        }
    }

}
