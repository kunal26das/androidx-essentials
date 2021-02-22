package androidx.essentials.playground.ui.home

import androidx.core.view.children
import androidx.essentials.core.lifecycle.observer.ViewModel
import androidx.essentials.core.preference.SharedPreferences
import androidx.essentials.core.preference.SharedPreferences.Companion.mutableLiveData
import androidx.essentials.core.utils.Resources
import androidx.essentials.playground.R

class HomeViewModel : ViewModel(), SharedPreferences {

    val destination by mutableLiveData<String>(KEY_DESTINATION)
    val libraryList = Resources.getMenu(R.menu.menu_library).menu.children.toList()

    companion object {
        const val KEY_DESTINATION = "destination"
    }

}