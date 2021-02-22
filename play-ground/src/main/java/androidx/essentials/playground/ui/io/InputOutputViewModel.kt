package androidx.essentials.playground.ui.io

import androidx.core.view.children
import androidx.essentials.core.injector.KoinComponent.inject
import androidx.essentials.core.lifecycle.observer.ViewModel
import androidx.essentials.core.utils.Resources
import androidx.essentials.playground.R
import androidx.essentials.preferences.SharedPreferences
import androidx.lifecycle.MutableLiveData

class InputOutputViewModel : ViewModel() {

    private val sharedPreferences by inject<SharedPreferences>()

    val selection = MutableLiveData(emptyArray<String>())
    val endDate = sharedPreferences.getMutableLiveData<Long>(KEY_END_DATE)
    val endTime = sharedPreferences.getMutableLiveData<Long>(KEY_END_TIME)
    val startDate = sharedPreferences.getMutableLiveData<Long>(KEY_START_DATE)
    val startTime = sharedPreferences.getMutableLiveData<Long>(KEY_START_TIME)
    val textInput = sharedPreferences.getMutableLiveData<String>(KEY_TEXT_INPUT)
    val isEditable = sharedPreferences.getMutableLiveData<Boolean>(KEY_IS_EDITABLE)
    val isMandatory = sharedPreferences.getMutableLiveData<Boolean>(KEY_IS_MANDATORY)
    val autoComplete = sharedPreferences.getMutableLiveData<String>(KEY_AUTO_COMPLETE)
    val singleSelection = sharedPreferences.getMutableLiveData<Boolean>(KEY_SINGLE_SELECTION)

    val libraryArray = Resources.getMenu(R.menu.menu_library).menu.children
        .toList().map { "${it.title}" }.toTypedArray()

    companion object {
        private const val KEY_END_DATE = "end_date"
        private const val KEY_END_TIME = "end_time"
        private const val KEY_START_DATE = "start_date"
        private const val KEY_START_TIME = "start_time"
        private const val KEY_TEXT_INPUT = "text_input"
        private const val KEY_IS_EDITABLE = "is_editable"
        private const val KEY_IS_MANDATORY = "is_mandatory"
        private const val KEY_AUTO_COMPLETE = "auto_complete"
        private const val KEY_SINGLE_SELECTION = "single_selection"
    }

}