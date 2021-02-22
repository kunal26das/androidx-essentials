package androidx.essentials.core.preference

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface SharedPreferences {

    companion object {

        fun Any.put(pair: Pair<String, Any?>) {
            val key = pair.first
            Preferences.edit().apply {
                when (pair.second) {
                    null -> remove(key)
                    else -> with(pair.second) {
                        when (this) {
                            is Int -> putInt(key, this)
                            is Long -> putLong(key, this)
                            is Float -> putFloat(key, this)
                            is String -> putString(key, this)
                            is Boolean -> putBoolean(key, this)
                            else -> Unit
                        }
                    }
                }
            }.apply()
        }

        inline fun <reified T> Any.get(key: String) = when (Preferences.contains(key)) {
            false -> null
            true -> when (T::class) {
                Int::class -> Preferences.getInt(key)
                Long::class -> Preferences.getLong(key)
                Float::class -> Preferences.getFloat(key)
                String::class -> Preferences.getString(key)
                Boolean::class -> Preferences.getBoolean(key)
                else -> null
            }
        } as? T

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