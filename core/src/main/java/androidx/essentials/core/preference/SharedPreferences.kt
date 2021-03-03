package androidx.essentials.core.preference

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object SharedPreferences {

    fun Any.put(pair: Pair<Enum<*>, Any?>) {
        val key = pair.first
        val value = pair.second
        Preferences.edit().apply {
            when (value) {
                null -> remove(key.name)
                else -> when (value) {
                    is Int -> putInt(key.name, value)
                    is Long -> putLong(key.name, value)
                    is Float -> putFloat(key.name, value)
                    is String -> putString(key.name, value)
                    is Boolean -> putBoolean(key.name, value)
                    else -> Unit
                }
            }
        }.apply()
    }

    inline fun <reified T> Any.get(key: Enum<*>): T? {
        with(Preferences) {
            return try {
                when (contains(key.name)) {
                    true -> when (T::class) {
                        Int::class -> getInt(key.name)
                        Long::class -> getLong(key.name)
                        Float::class -> getFloat(key.name)
                        String::class -> getString(key.name)
                        Boolean::class -> getBoolean(key.name)
                        else -> null
                    }
                    false -> null
                } as? T
            } catch (e: ClassCastException) {
                null
            }
        }
    }

    inline fun <reified T> Any.liveData(key: Enum<*>): Lazy<LiveData<T?>> = lazy {
        object : LiveData<T?>(), SharedPreferences.OnSharedPreferenceChangeListener {

            private val key = key

            override fun onActive() {
                Preferences.registerOnSharedPreferenceChangeListener(this)
            }

            override fun getValue() = get<T>(key)

            override fun onSharedPreferenceChanged(
                sharedPreferences: SharedPreferences?,
                key: String?
            ) {
                if (this.key.name == key) {
                    setValue(get<T>(this.key))
                }
            }

            override fun onInactive() {
                Preferences.unregisterOnSharedPreferenceChangeListener(this)
            }
        }
    }

    inline fun <reified T> Any.mutableLiveData(key: Enum<*>): Lazy<MutableLiveData<T?>> = lazy {
        object : MutableLiveData<T?>(), SharedPreferences.OnSharedPreferenceChangeListener {

            private val key = key

            override fun onActive() {
                Preferences.registerOnSharedPreferenceChangeListener(this)
            }

            override fun getValue() = get<T>(key)

            override fun setValue(value: T?) {
                if (value != getValue()) {
                    super.setValue(value)
                    put(Pair(key, value))
                }
            }

            override fun onSharedPreferenceChanged(
                sharedPreferences: SharedPreferences?,
                key: String?
            ) {
                if (this.key.name == key) {
                    get<T>(this.key)?.let { setValue(it) }
                }
            }

            override fun onInactive() {
                Preferences.unregisterOnSharedPreferenceChangeListener(this)
            }

        }
    }

}