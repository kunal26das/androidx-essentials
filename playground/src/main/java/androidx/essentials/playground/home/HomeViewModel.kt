package androidx.essentials.playground.home

import androidx.essentials.playground.library.Library
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    val libraries get() = Library.values().toList()

}