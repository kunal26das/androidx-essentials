package androidx.essentials.playground.ui

import android.os.Bundle
import android.widget.Toast
import androidx.essentials.core.ui.Activity
import androidx.essentials.events.Events
import androidx.essentials.playground.R
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_play_ground.*


class PlayGroundActivity : Activity() {

    private lateinit var navController: NavController
    override val layout = R.layout.activity_play_ground
    private lateinit var appBarConfiguration: AppBarConfiguration
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
        (supportFragmentManager.findFragmentById(R.id.playGroundNavigation) as NavHostFragment).navController.apply {
            navController = this
            navigationView.setupWithNavController(this)
            appBarConfiguration = AppBarConfiguration(graph, drawerLayout)
            setupActionBarWithNavController(this, appBarConfiguration)
        }
    }

    override fun initObservers() {
        Events.subscribe(String::class.java) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

}
