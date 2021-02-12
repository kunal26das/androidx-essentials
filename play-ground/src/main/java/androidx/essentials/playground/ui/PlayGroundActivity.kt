package androidx.essentials.playground.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.essentials.core.lifecycle.Activity
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.ActivityPlayGroundBinding
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

class PlayGroundActivity : Activity() {

    private lateinit var navController: NavController
    override val layout = R.layout.activity_play_ground
    override val viewModel by viewModel<PlayGroundViewModel>()
    private lateinit var appBarConfiguration: AppBarConfiguration
    override val binding by dataBinding<ActivityPlayGroundBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAppBarLayout()
        initNavigation()
    }

    private fun initAppBarLayout() {
        binding.apply {
            setSupportActionBar(appBarLayout.toolbar)
            appBarLayout.toolbar.setTitleTextAppearance(
                baseContext, R.style.ToolbarTitle
            )
        }
    }

    private fun initNavigation() {
        (supportFragmentManager.findFragmentById(R.id.playGroundNavigation) as NavHostFragment).navController.apply {
            navController = this
            binding.navigationView.setupWithNavController(this)
            appBarConfiguration = AppBarConfiguration(graph, binding.drawerLayout)
            setupActionBarWithNavController(this, appBarConfiguration)
        }
    }

    override fun initObservers() {
        super.initObservers()
        String::class.java.subscribe { toast(it) }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    companion object {
        fun start(context: Context?, flags: Int = Intent.FLAG_ACTIVITY_NEW_TASK): Activity? {
            context?.startActivity(Intent(context, PlayGroundActivity::class.java).apply {
                this.flags = flags
            })
            return context as? Activity
        }
    }

}
