package androidx.essentials.network

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * String key
 */

inline operator fun <reified T> SharedPreferences.get(key: String) = when (T::class) {
    Int::class -> getInt(key)
    Long::class -> getLong(key)
    Float::class -> getFloat(key)
    String::class -> getString(key)
    Boolean::class -> getBoolean(key)
    else -> null
} as? T

fun SharedPreferences.getInt(key: String) = when (contains(key)) {
    true -> getInt(key, 0)
    false -> null
}

fun SharedPreferences.getLong(key: String) = when (contains(key)) {
    true -> getLong(key, 0L)
    false -> null
}

fun SharedPreferences.getFloat(key: String) = when (contains(key)) {
    true -> getFloat(key, 0f)
    false -> null
}

fun SharedPreferences.getString(key: String) = when (contains(key)) {
    true -> getString(key, null)
    false -> null
}

fun SharedPreferences.getBoolean(key: String) = when (contains(key)) {
    true -> getBoolean(key, false)
    false -> null
}

operator fun SharedPreferences.set(key: String, value: Any?) {
    edit().apply {
        when (value) {
            null -> remove(key)
            else -> when (value) {
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
                is String -> when {
                    value.isEmpty() -> remove(key)
                    else -> putString(key, value)
                }
                is Boolean -> putBoolean(key, value)
                else -> Unit
            }
        }
    }?.apply()
}

inline fun <reified T> SharedPreferences.liveData(key: String): LiveData<T?> =
    object : LiveData<T?>(), SharedPreferences.OnSharedPreferenceChangeListener {

        override fun onActive() {
            registerOnSharedPreferenceChangeListener(this)
        }

        override fun getValue() = get<T>(key)

        override fun onSharedPreferenceChanged(
            sharedPreferences: SharedPreferences,
            _key: String
        ) {
            if (_key == key) {
                setValue(get<T>(key))
            }
        }

        override fun onInactive() {
            unregisterOnSharedPreferenceChangeListener(this)
        }
    }

inline fun <reified T> SharedPreferences.mutableLiveData(key: String): MutableLiveData<T?> =
    object : MutableLiveData<T?>(), SharedPreferences.OnSharedPreferenceChangeListener {

        override fun onActive() {
            registerOnSharedPreferenceChangeListener(this)
        }

        override fun getValue() = get<T>(key)

        override fun setValue(value: T?) {
            if (value != getValue()) {
                super.setValue(value)
                set(key, value)
            }
        }

        override fun onSharedPreferenceChanged(
            sharedPreferences: SharedPreferences,
            _key: String
        ) {
            if (_key == key) {
                value = get<T>(key)
            }
        }

        override fun onInactive() {
            unregisterOnSharedPreferenceChangeListener(this)
        }
    }

inline fun <reified T> SharedPreferences.mutableLiveDataOf(
    key: String
): Lazy<MutableLiveData<T?>> = lazy {
    mutableLiveData(key)
}

inline fun <reified T> SharedPreferences.liveDataOf(
    key: String
): Lazy<LiveData<T?>> = lazy {
    liveData(key)
}

/**
 * Enum key
 */

inline operator fun <reified T> SharedPreferences.get(enum: Enum<*>) = get<T>(enum.name)

fun SharedPreferences.getInt(enum: Enum<*>) = getInt(enum.name)
fun SharedPreferences.getLong(enum: Enum<*>) = getLong(enum.name)
fun SharedPreferences.getFloat(enum: Enum<*>) = getFloat(enum.name)
fun SharedPreferences.getString(enum: Enum<*>) = getString(enum.name)
fun SharedPreferences.getBoolean(enum: Enum<*>) = getBoolean(enum.name)

operator fun SharedPreferences.set(key: Enum<*>, value: Any?) = set(key.name, value)

inline fun <reified T> SharedPreferences.liveData(enum: Enum<*>) =
    liveData<T>(enum.name)

inline fun <reified T> SharedPreferences.liveDataOf(enum: Enum<*>) =
    liveDataOf<T>(enum.name)

inline fun <reified T> SharedPreferences.mutableLiveData(enum: Enum<*>) =
    mutableLiveData<T>(enum.name)

inline fun <reified T> SharedPreferences.mutableLiveDataOf(enum: Enum<*>) =
    mutableLiveDataOf<T>(enum.name)