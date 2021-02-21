package androidx.essentials.playground.ui.home

import android.os.Bundle
import android.view.View
import androidx.essentials.core.lifecycle.owner.NavigationActivity
import androidx.essentials.extensions.Coroutines.default
import androidx.essentials.extensions.Coroutines.main
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.ActivityHomeBinding
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

class HomeActivity : NavigationActivity() {

    override val layout = R.layout.activity_home
    override val navHostFragment = R.id.playGroundNavigation

    override val viewModel by viewModel<HomeViewModel>()
    override val binding by dataBinding<ActivityHomeBinding>()

    override val appBarConfiguration by lazy {
        AppBarConfiguration(navController.graph, binding.drawerLayout)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNavigation()
    }

    private fun initNavigation() {
        binding.navigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.appBarLayout.toolbar.setTitleTextAppearance(this, R.style.ToolbarTitle)
    }

    override fun initObservers() {
        super.initObservers()
        String::class.java.subscribe { toast(it) }
        Destination::class.java.subscribe {
            navController.default {
                if ((it.id in graph.map { it.id }) and
                    (currentDestination?.id != it.id)
                ) main { navigate(it.id) }
            }
        }
    }

    enum class Destination(val id: Int) {
        Backdrop(R.id.backdrop),
        Core(R.id.core),
        Firebase(R.id.firebase),
        IO(R.id.io),
        Location(R.id.location),
        Network(R.id.network),
        SharedPreferences(R.id.sharedPreferences);

        companion object {
            fun getById(id: Int) = values().firstOrNull { it.id == id }
        }
    }

}
