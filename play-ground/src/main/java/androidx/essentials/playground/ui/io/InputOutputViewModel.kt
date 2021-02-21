package androidx.essentials.playground.ui.io

import androidx.core.view.children
import androidx.essentials.core.lifecycle.observer.ViewModel
import androidx.essentials.core.utils.Resources
import androidx.essentials.playground.R
import androidx.lifecycle.MutableLiveData

class InputOutputViewModel : ViewModel() {

    val endDate = MutableLiveData<Long>()
    val endTime = MutableLiveData<Long>()
    val startDate = MutableLiveData<Long>()
    val startTime = MutableLiveData<Long>()
    val textInput = MutableLiveData<String>()
    val autoComplete = MutableLiveData<String>()
    val selection = MutableLiveData(emptyArray<String>())

    val isEditable = MutableLiveData(true)
    val isMandatory = MutableLiveData(true)
    val singleSelection = MutableLiveData(true)

    val libraryArray = Resources.getMenu(R.menu.menu_library).menu.children
        .toList().map { "${it.title}" }.toTypedArray()

}