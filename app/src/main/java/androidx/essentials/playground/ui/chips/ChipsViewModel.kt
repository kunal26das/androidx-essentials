package androidx.essentials.playground.ui.chips

import androidx.core.view.children
import androidx.essentials.core.lifecycle.observer.ViewModel
import androidx.essentials.core.utils.Resources
import androidx.essentials.playground.Preference
import androidx.essentials.playground.R
import androidx.essentials.preferences.SharedPreferences
import androidx.essentials.preferences.SharedPreferences.Companion.mutableLiveData
import androidx.lifecycle.MutableLiveData

class ChipsViewModel : ViewModel(), SharedPreferences {

    val isCheckable by mutableLiveData<Boolean>(Preference.IS_CHECKABLE)
    val isMandatory by mutableLiveData<Boolean>(Preference.IS_MANDATORY)
    val isSingleSelection by mutableLiveData<Boolean>(Preference.IS_SINGLE_SELECTION)
    val libraries = MutableLiveData(Resources.getMenu(R.menu.menu_library)?.children?.map {
        "${it.title}"
    }?.toSet())

    //    val selection = Transformations.map(libraries) { it?.filter { it.isChecked }?.toSet() }
    val selection = MutableLiveData<Set<String>>()

}