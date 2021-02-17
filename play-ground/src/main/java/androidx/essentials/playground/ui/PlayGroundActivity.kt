package androidx.essentials.playground.ui

import android.os.Bundle
import android.view.View
import androidx.essentials.core.lifecycle.owner.NavigationActivity
import androidx.essentials.extensions.Coroutines.default
import androidx.essentials.extensions.Coroutines.main
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.ActivityPlayGroundBinding
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

class PlayGroundActivity : NavigationActivity() {

    override val layout = R.layout.activity_play_ground
    override val navHostFragment = R.id.playGroundNavigation

    override val viewModel by viewModel<PlayGroundViewModel>()
    override val binding by dataBinding<ActivityPlayGroundBinding>()

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
        Extensions(R.id.extensions),
        List(R.id.list),
        Location(R.id.location),
        Network(R.id.network),
        Resources(R.id.resources);

        companion object {
            fun getById(id: Int) = values().firstOrNull { it.id == id }
        }
    }

}
