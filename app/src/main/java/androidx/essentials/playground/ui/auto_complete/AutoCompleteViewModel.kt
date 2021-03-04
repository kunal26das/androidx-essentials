package androidx.essentials.playground.ui.auto_complete

import androidx.core.view.children
import androidx.essentials.core.lifecycle.observer.ViewModel
import androidx.essentials.core.preference.SharedPreferences.mutableLiveData
import androidx.essentials.core.utils.Resources
import androidx.essentials.playground.Preference
import androidx.essentials.playground.R

class AutoCompleteViewModel : ViewModel() {

    val keyboard by mutableLiveData<Boolean>(Preference.KEYBOARD)
    val isEditable by mutableLiveData<Boolean>(Preference.IS_EDITABLE)
    val isMandatory by mutableLiveData<Boolean>(Preference.IS_MANDATORY)
    val autoComplete by mutableLiveData<String>(Preference.AUTO_COMPLETE)
    val isHintEnabled by mutableLiveData<Boolean>(Preference.IS_HINT_ENABLED)

    val libraries = Resources.getMenu(R.menu.menu_library).children
        .toList().map { "${it.title}" }.toTypedArray()

}