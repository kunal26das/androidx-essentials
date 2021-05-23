package androidx.essentials.activity

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration

abstract class NavigationActivity : Activity() {

    abstract val navHostFragmentId: Int
    protected lateinit var navController: NavController
    abstract val appBarConfiguration: AppBarConfiguration
    protected lateinit var navHostFragment: NavHostFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNavigation()
    }

    @CallSuper
    protected open fun initNavigation() {
        navHostFragment =
            supportFragmentManager.findFragmentById(navHostFragmentId) as NavHostFragment
        navController = navHostFragment.navController
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