package androidx.essentials.playground.ui

import android.os.Bundle
import android.widget.Toast
import androidx.essentials.core.ui.Activity
import androidx.essentials.events.Events
import androidx.essentials.playground.R
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_play_ground.*


class PlayGroundActivity : Activity(true) {

    private lateinit var navController: NavController
    override val layout = R.layout.activity_play_ground
    override val viewModel by viewModel<PlayGroundViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAppBarLayout()
        initNavigation()
    }

    private fun initAppBarLayout() {
        appBarLayout.apply {
            setSupportActionBar(toolbar)
            toolbar.setTitleTextAppearance(
                baseContext,
                R.style.ToolbarTitle
            )
        }
    }

    private fun initNavigation() {
        navController =
            (supportFragmentManager.findFragmentById(R.id.playGroundNavigation) as NavHostFragment).navController
        setupActionBarWithNavController(
            navController,
            AppBarConfiguration(navController.graph, drawerLayout)
        )
        navigationView.setupWithNavController(navController)
    }

    override fun initObservers() {
        Events.subscribe(String::class.java) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

}
