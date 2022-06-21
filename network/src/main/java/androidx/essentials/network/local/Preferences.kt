package androidx.essentials.network.local

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class Preferences @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun clear() = edit.clear().commit()
    val all get() = sharedPreferences.all
    private val edit get() = sharedPreferences.edit()

    fun contains(key: String?): Boolean {
        if (key.isNullOrEmpty()) return false
        return sharedPreferences.contains(key)
    }

    inline operator fun <reified T> get(key: String?): T? {
        if (key.isNullOrEmpty()) return null
        return when (T::class) {
            Int::class -> getInt(key)
            Long::class -> getLong(key)
            Float::class -> getFloat(key)
            String::class -> getString(key)
            Boolean::class -> getBoolean(key)
            else -> null
        } as? T
    }

    fun getInt(key: String?) = when (contains(key)) {
        true -> sharedPreferences.getInt(key, 0)
        false -> null
    }

    fun getLong(key: String?) = when (contains(key)) {
        true -> sharedPreferences.getLong(key, 0L)
        false -> null
    }

    fun getFloat(key: String?) = when (contains(key)) {
        true -> sharedPreferences.getFloat(key, 0f)
        false -> null
    }

    fun getString(key: String?) = when (contains(key)) {
        true -> sharedPreferences.getString(key, null)
        false -> null
    }

    fun getBoolean(key: String?) = when (contains(key)) {
        true -> sharedPreferences.getBoolean(key, false)
        false -> null
    }

    operator fun set(key: String?, value: Any?) {
        if (key.isNullOrEmpty()) return
        edit.apply {
            when (value) {
                null -> remove(key)
                else -> when (value) {
                    is Int -> putInt(key, value)
                    is Long -> putLong(key, value)
                    is Float -> putFloat(key, value)
                    is String -> putString(key, value)
                    is Boolean -> putBoolean(key, value)
                    else -> Unit
                }
            }
        }?.apply()
    }

    inline operator fun <reified T> get(enum: Enum<*>?) = get<T>(enum?.name)

    fun getInt(enum: Enum<*>?) = getInt(enum?.name)
    fun getLong(enum: Enum<*>?) = getLong(enum?.name)
    fun getFloat(enum: Enum<*>?) = getFloat(enum?.name)
    fun getString(enum: Enum<*>?) = getString(enum?.name)
    fun getBoolean(enum: Enum<*>?) = getBoolean(enum?.name)

    operator fun set(key: Enum<*>, value: Any?) = set(key.name, value)

    fun registerOnSharedPreferenceChangeListener(
        listener: SharedPreferences.OnSharedPreferenceChangeListener
    ) = sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

    fun unregisterOnSharedPreferenceChangeListener(
        listener: SharedPreferences.OnSharedPreferenceChangeListener
    ) = sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)

    inline fun <reified T> liveData(key: String?): Lazy<LiveData<T?>> = lazy {
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
    }

    inline fun <reified T> mutableLiveData(key: String?): Lazy<MutableLiveData<T?>> = lazy {
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
    }

    inline fun <reified T> liveData(enum: Enum<*>?) = liveData<T>(enum?.name)
    inline fun <reified T> mutableLiveData(enum: Enum<*>?) = mutableLiveData<T>(enum?.name)

}