package androidx.essentials.core.lifecycle.owner

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp

abstract class NavigationActivity : Activity() {

    abstract val navHostFragment: Int
    protected lateinit var navController: NavController
    abstract val appBarConfiguration: AppBarConfiguration

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNavigation()
    }

    @CallSuper
    protected open fun initNavigation() {
        (supportFragmentManager.findFragmentById(navHostFragment) as NavHostFragment).apply {
            this@NavigationActivity.navController = navController
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

}