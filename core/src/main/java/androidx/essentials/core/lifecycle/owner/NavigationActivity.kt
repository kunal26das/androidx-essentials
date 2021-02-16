package androidx.essentials.core.lifecycle.owner

import android.os.Bundle
import android.view.View
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
        navController =
            (supportFragmentManager.findFragmentById(navHostFragment) as NavHostFragment).navController
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

}