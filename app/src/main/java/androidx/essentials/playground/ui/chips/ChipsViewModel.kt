package androidx.essentials.playground.ui.chips

import android.widget.Checkable
import androidx.core.view.children
import androidx.essentials.core.lifecycle.observer.ViewModel
import androidx.essentials.core.preference.SharedPreferences.mutableLiveData
import androidx.essentials.core.utils.Resources
import androidx.essentials.playground.Preference
import androidx.essentials.playground.R

class ChipsViewModel : ViewModel() {

    val isMandatory by mutableLiveData<Boolean>(Preference.IS_MANDATORY)
    val singleSelection by mutableLiveData<Boolean>(Preference.SINGLE_SELECTION)
    val libraries: List<Checkable>? = Resources.getMenu(R.menu.menu_library)?.children?.map {
        object : Checkable {
            override fun setChecked(checked: Boolean) {
                it.isChecked = checked
            }

            override fun isChecked() = it.isChecked

            override fun toggle() {
                it.isChecked = !it.isChecked
            }

            override fun toString() = "${it.title}"
        }
    }?.toList()

}