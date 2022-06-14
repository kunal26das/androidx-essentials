package androidx.essentials.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration

abstract class NavigationActivity : Activity() {

    abstract val navHostFragmentId: Int
    abstract val appBarConfiguration: AppBarConfiguration
    protected val navController get() = navHostFragment.navController
    protected val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(navHostFragmentId) as NavHostFragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navHostFragment
    }

}