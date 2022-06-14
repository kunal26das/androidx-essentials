package androidx.essentials.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
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

    protected fun navigate(@IdRes destination: Int): Boolean {
        navController.apply {
            if (destination in graph.map { it.id }) {
                if (currentDestination?.id != destination) {
                    return try {
                        navigate(destination)
                        true
                    } catch (e: Throwable) {
                        false
                    }
                }
            }
            return false
        }
    }

}