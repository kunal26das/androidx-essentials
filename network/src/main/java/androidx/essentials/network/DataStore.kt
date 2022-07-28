package androidx.essentials.network

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * String key
 */

@Suppress("UNCHECKED_CAST")
inline fun <reified T> preferencesKey(key: String) = when (T::class) {
    Int::class -> intPreferencesKey(key)
    Long::class -> longPreferencesKey(key)
    Float::class -> floatPreferencesKey(key)
    Double::class -> doublePreferencesKey(key)
    String::class -> stringPreferencesKey(key)
    Boolean::class -> booleanPreferencesKey(key)
    MutableSet::class -> stringPreferencesKey(key)
    else -> null
} as? Preferences.Key<T>

inline fun <reified T> Preferences.get(key: String): T? {
    return preferencesKey<T>(key)?.let {
        if (contains(it)) get(it) else null
    }
}

suspend inline fun <reified T> DataStore<Preferences>.set(
    key: String, value: T?
) = updateData {
    it.toMutablePreferences().apply {
        preferencesKey<T>(key)?.let {
            when (value) {
                null -> remove(it)
                else -> set(it, value)
            }
        }
    }
}

inline fun <reified T> DataStore<Preferences>.liveData(
    key: String, coroutineContext: CoroutineContext = EmptyCoroutineContext
) = data.map { it.get<T>(key) }.asLiveData(coroutineContext)

inline fun <reified T> DataStore<Preferences>.liveDataOf(
    key: String, coroutineContext: CoroutineContext = EmptyCoroutineContext
) = lazy {
    liveData<T>(key, coroutineContext)
}

inline fun <reified T> DataStore<Preferences>.mutableLiveData(
    key: String, coroutineContext: CoroutineContext = EmptyCoroutineContext
) = object : MediatorLiveData<T?>() {

    private var job: Job? = null
    private val coroutineScope = CoroutineScope(coroutineContext)

    init {
        addSource(liveData<T>(key)) {
            value = it
        }
    }

    override fun setValue(value: T?) {
        if (value != getValue()) {
            super.setValue(value)
            job?.cancel()
            job = coroutineScope.launch {
                set<T>(key, value)
            }
        }
    }
}

inline fun <reified T> DataStore<Preferences>.mutableLiveDataOf(
    key: String, coroutineContext: CoroutineContext = EmptyCoroutineContext
) = lazy {
    mutableLiveData<T>(key, coroutineContext)
}

/**
 * Enum key
 */

inline fun <reified T> preferencesKey(key: Enum<*>) = preferencesKey<T>(key.name)

inline fun <reified T> Preferences.get(key: Enum<*>) = get<T>(key.name)

suspend inline fun <reified T> DataStore<Preferences>.set(
    key: Enum<*>, value: T?
) = set<T>(key.name, value)

inline fun <reified T> DataStore<Preferences>.liveData(
    key: Enum<*>, coroutineContext: CoroutineContext = EmptyCoroutineContext
) = liveData<T>(key.name, coroutineContext)

inline fun <reified T> DataStore<Preferences>.liveDataOf(
    key: Enum<*>, coroutineContext: CoroutineContext = EmptyCoroutineContext
) = lazy {
    liveData<T>(key, coroutineContext)
}

inline fun <reified T> DataStore<Preferences>.mutableLiveData(
    key: Enum<*>, coroutineContext: CoroutineContext = EmptyCoroutineContext
) = mutableLiveData<T>(key.name, coroutineContext)

inline fun <reified T> DataStore<Preferences>.mutableLiveDataOf(
    key: Enum<*>, coroutineContext: CoroutineContext = EmptyCoroutineContext
) = lazy {
    mutableLiveData<T>(key, coroutineContext)
}