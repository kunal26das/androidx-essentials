package androidx.essentials.playground.chips

import android.content.SharedPreferences
import androidx.essentials.network.mutableLiveDataOf
import androidx.essentials.playground.Feature
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChipsViewModel @Inject constructor(
    sharedPreferences: SharedPreferences
) : ViewModel() {

    val selection = MutableLiveData<Set<String>>()
    val features get() = Feature.values().map { it.name }.toSet()
    val isCheckable by sharedPreferences.mutableLiveDataOf<Boolean>(KEY_CHECKABLE)
    val isMandatory by sharedPreferences.mutableLiveDataOf<Boolean>(KEY_MANDATORY)
    val isSingleSelection by sharedPreferences.mutableLiveDataOf<Boolean>(KEY_SINGLE_SELECTION)

    companion object {
        private const val KEY_CHECKABLE = "checkable"
        private const val KEY_MANDATORY = "mandatory"
        private const val KEY_SINGLE_SELECTION = "single_selection"
    }

}