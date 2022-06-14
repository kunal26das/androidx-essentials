package androidx.essentials.playground.home

import androidx.core.view.children
import androidx.essentials.playground.R
import androidx.essentials.playground.Resources
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    val libraries = Resources.getMenu(R.menu.menu_library)?.children?.toList()

}