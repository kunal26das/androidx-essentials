package androidx.essentials.playground.ui.chips

import androidx.core.view.children
import androidx.essentials.core.lifecycle.observer.ViewModel
import androidx.essentials.core.preference.SharedPreferences.mutableLiveData
import androidx.essentials.core.utils.Resources
import androidx.essentials.playground.Preference
import androidx.essentials.playground.R
import androidx.lifecycle.MutableLiveData

class ChipsViewModel : ViewModel() {

    val isCheckable by mutableLiveData<Boolean>(Preference.IS_CHECKABLE)
    val isMandatory by mutableLiveData<Boolean>(Preference.IS_MANDATORY)
    val isSingleSelection by mutableLiveData<Boolean>(Preference.IS_SINGLE_SELECTION)
    val libraries = MutableLiveData(Resources.getMenu(R.menu.menu_library)?.children?.map {
        "${it.title}"
    }?.toSet())

    //    val selection = Transformations.map(libraries) { it?.filter { it.isChecked }?.toSet() }
    val selection = MutableLiveData<Set<String>>()

}