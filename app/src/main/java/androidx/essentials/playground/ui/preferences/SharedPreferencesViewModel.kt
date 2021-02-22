package androidx.essentials.playground.ui.preferences

import androidx.essentials.core.injector.KoinComponent.inject
import androidx.essentials.core.lifecycle.observer.ViewModel
import androidx.essentials.preferences.EncryptedSharedPreferences

class SharedPreferencesViewModel : ViewModel() {

    private val sharedPreferences by inject<EncryptedSharedPreferences>()

    val _int = sharedPreferences.getMutableLiveData<Int>(KEY_INT)
    val _long = sharedPreferences.getMutableLiveData<Long>(KEY_LONG)
    val _float = sharedPreferences.getMutableLiveData<Float>(KEY_FLOAT)
    val _string = sharedPreferences.getMutableLiveData<String>(KEY_STRING)
    val _boolean = sharedPreferences.getMutableLiveData<Boolean>(KEY_BOOLEAN)

    companion object {
        private const val KEY_INT = "int"
        private const val KEY_LONG = "long"
        private const val KEY_FLOAT = "float"
        private const val KEY_STRING = "string"
        private const val KEY_BOOLEAN = "boolean"
    }

}