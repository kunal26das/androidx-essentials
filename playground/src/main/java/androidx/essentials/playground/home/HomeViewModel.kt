package androidx.essentials.playground.home

import androidx.core.view.children
import androidx.essentials.application.Resources
import androidx.essentials.playground.R
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    val libraries = Resources.getMenu(R.menu.menu_library)?.children?.toList()

}