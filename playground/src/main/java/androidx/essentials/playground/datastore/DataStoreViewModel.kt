package androidx.essentials.playground.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.essentials.network.mutableLiveDataOf
import androidx.essentials.playground.Constant
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(
    dataStore: DataStore<Preferences>
) : ViewModel(), Constant {

    val int by dataStore.mutableLiveDataOf<Int>(KEY_INT)
    val long by dataStore.mutableLiveDataOf<Long>(KEY_LONG)
    val float by dataStore.mutableLiveDataOf<Float>(KEY_FLOAT)
    val double by dataStore.mutableLiveDataOf<Double>(KEY_DOUBLE)
    val string by dataStore.mutableLiveDataOf<String>(KEY_STRING)
    val boolean by dataStore.mutableLiveDataOf<Boolean>(KEY_BOOLEAN)

}