package androidx.essentials.network

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

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

inline fun <reified T> MutablePreferences.set(key: String, value: T?) {
    preferencesKey<T>(key)?.let {
        when (value) {
            null -> remove(it)
            else -> set(it, value)
        }
    }
}

fun Preferences.getInt(key: String) = get<Int>(key)
fun Preferences.getLong(key: String) = get<Long>(key)
fun Preferences.getFloat(key: String) = get<Float>(key)
fun Preferences.getDouble(key: String) = get<Double>(key)
fun Preferences.getString(key: String) = get<String>(key)
fun Preferences.getBoolean(key: String) = get<Boolean>(key)
fun Preferences.getStringSet(key: String) = get<MutableSet<String>>(key)

fun MutablePreferences.setInt(key: String, value: Int?) = set(key, value)
fun MutablePreferences.setLong(key: String, value: Long?) = set(key, value)
fun MutablePreferences.setFloat(key: String, value: Float?) = set(key, value)
fun MutablePreferences.setDouble(key: String, value: Double?) = set(key, value)
fun MutablePreferences.setString(key: String, value: String?) = set(key, value)
fun MutablePreferences.setBoolean(key: String, value: Boolean?) = set(key, value)
fun MutablePreferences.setStringSet(key: String, value: Set<String>?) = set(key, value)

suspend fun DataStore<Preferences>.getPreferences() =
    data.firstOrNull()?.toPreferences()

suspend fun DataStore<Preferences>.getMutablePreferences() =
    getPreferences()?.toMutablePreferences()

suspend inline fun <reified T> DataStore<Preferences>.get(key: String) =
    getPreferences()?.get<T>(key)

suspend inline fun <reified T> DataStore<Preferences>.set(key: String, value: T?) = updateData {
    it.toMutablePreferences().also {
        it.set<T>(key, value)
    }
}

suspend fun DataStore<Preferences>.getInt(key: String) = get<Int>(key)
suspend fun DataStore<Preferences>.getLong(key: String) = get<Long>(key)
suspend fun DataStore<Preferences>.getFloat(key: String) = get<Float>(key)
suspend fun DataStore<Preferences>.getDouble(key: String) = get<Double>(key)
suspend fun DataStore<Preferences>.getString(key: String) = get<String>(key)
suspend fun DataStore<Preferences>.getBoolean(key: String) = get<Boolean>(key)
suspend fun DataStore<Preferences>.getStringSet(key: String) = get<MutableSet<String>>(key)

suspend fun DataStore<Preferences>.setInt(key: String, value: Int?) = set(key, value)
suspend fun DataStore<Preferences>.setLong(key: String, value: Long?) = set(key, value)
suspend fun DataStore<Preferences>.setFloat(key: String, value: Float?) = set(key, value)
suspend fun DataStore<Preferences>.setDouble(key: String, value: Double?) = set(key, value)
suspend fun DataStore<Preferences>.setString(key: String, value: String?) = set(key, value)
suspend fun DataStore<Preferences>.setBoolean(key: String, value: Boolean?) = set(key, value)
suspend fun DataStore<Preferences>.setBoolean(key: String, value: MutableSet<String>?) =
    set(key, value)

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

    private val coroutineScope = CoroutineScope(coroutineContext)

    init {
        addSource(liveData<T>(key)) {
            value = it
        }
    }

    override fun setValue(value: T?) {
        if (value != getValue()) {
            super.setValue(value)
            coroutineScope.launch {
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