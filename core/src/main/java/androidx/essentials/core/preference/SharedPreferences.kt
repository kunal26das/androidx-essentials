package androidx.essentials.core.preference

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface SharedPreferences {

    companion object {

        fun Any.put(pair: Pair<String, Any?>) {
            val key = pair.first
            val value = pair.second
            Preferences.edit().apply {
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
            }.apply()
        }

        inline fun <reified T> Any.get(key: String): T? {
            with(Preferences) {
                return when (contains(key)) {
                    true -> when (T::class) {
                        Int::class -> getInt(key)
                        Long::class -> getLong(key)
                        Float::class -> getFloat(key)
                        String::class -> getString(key)
                        Boolean::class -> getBoolean(key)
                        else -> null
                    }
                    false -> null
                } as? T
            }
        }

        inline fun <reified T> Any.liveData(key: String): Lazy<LiveData<T?>> = lazy {
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
                    if (this.key == key) {
                        setValue(get<T>(key))
                    }
                }

                override fun onInactive() {
                    Preferences.unregisterOnSharedPreferenceChangeListener(this)
                }
            }
        }

        inline fun <reified T> Any.mutableLiveData(key: String): Lazy<MutableLiveData<T?>> = lazy {
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
                    if (this.key == key) {
                        get<T>(key)?.let { setValue(it) }
                    }
                }

                override fun onInactive() {
                    Preferences.unregisterOnSharedPreferenceChangeListener(this)
                }

            }
        }

    }

}