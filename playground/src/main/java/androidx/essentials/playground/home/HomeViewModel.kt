package androidx.essentials.playground.home

import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    val libraries get() = Feature.values().toList()

}