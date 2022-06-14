package androidx.essentials.playground.chips

import androidx.core.view.children
import androidx.essentials.playground.Preference
import androidx.essentials.playground.R
import androidx.essentials.playground.Resources
import androidx.essentials.preferences.SharedPreferences.mutableLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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