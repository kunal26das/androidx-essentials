package androidx.essentials.playground.ui.chips

import androidx.core.view.children
import androidx.essentials.core.lifecycle.observer.ViewModel
import androidx.essentials.core.preference.SharedPreferences.mutableLiveData
import androidx.essentials.core.utils.Resources
import androidx.essentials.playground.Preference
import androidx.essentials.playground.R
import androidx.lifecycle.MutableLiveData

class ChipsViewModel : ViewModel() {

    val selection = MutableLiveData(emptyArray<String>())
    val isMandatory by mutableLiveData<Boolean>(Preference.IS_MANDATORY)
    val singleSelection by mutableLiveData<Boolean>(Preference.SINGLE_SELECTION)

    val libraries = Resources.getMenu(R.menu.menu_library).children
        .toList().map { "${it.title}" }.toTypedArray()

}