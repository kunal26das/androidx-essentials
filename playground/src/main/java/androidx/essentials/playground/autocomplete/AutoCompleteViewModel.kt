package androidx.essentials.playground.autocomplete

import android.view.MenuItem
import androidx.core.view.children
import androidx.essentials.playground.Preference
import androidx.essentials.playground.R
import androidx.essentials.playground.Resources
import androidx.essentials.preferences.SharedPreferences.mutableLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AutoCompleteViewModel : ViewModel() {

    val autoComplete = MutableLiveData<MenuItem>()
    val filter by mutableLiveData<Boolean>(Preference.FILTER)
    val keyboard by mutableLiveData<Boolean>(Preference.KEYBOARD)
    val isEditable by mutableLiveData<Boolean>(Preference.IS_EDITABLE)
    val isMandatory by mutableLiveData<Boolean>(Preference.IS_MANDATORY)
    val isHintEnabled by mutableLiveData<Boolean>(Preference.IS_HINT_ENABLED)
    val libraries = Resources.getMenu(R.menu.menu_library)?.children?.toSet()?.toTypedArray()

}