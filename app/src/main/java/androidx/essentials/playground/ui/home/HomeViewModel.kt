package androidx.essentials.playground.ui.home

import androidx.core.view.children
import androidx.essentials.core.lifecycle.observer.ViewModel
import androidx.essentials.core.utils.Resources
import androidx.essentials.playground.R

class HomeViewModel : ViewModel() {

    val libraries = Resources.getMenu(R.menu.menu_library).children.toList()

}