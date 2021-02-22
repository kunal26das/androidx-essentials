package androidx.essentials.preferences

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.*

open class SharedPreferences(context: Context, name: String = context.packageName) {

    val ALL get() = sharedPreferences.all
    private val editor get() = sharedPreferences.edit()
    protected val locale: Locale by lazy { Locale.getDefault() }
    open val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(name.toLowerCase(locale), MODE_PRIVATE)
    }

    val all: LiveData<Map<String, Any?>> by lazy {
        object : LiveData<Map<String, Any?>>(ALL),
            SharedPreferences.OnSharedPreferenceChangeListener {

            override fun onActive() {
                super.onActive()
                sharedPreferences.registerOnSharedPreferenceChangeListener(this)
            }

            override fun getValue() = ALL

            override fun onSharedPreferenceChanged(
                sharedPreferences: SharedPreferences?,
                key: String?
            ) {
                value = ALL
            }

            override fun onInactive() {
                sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
                super.onInactive()
            }
        }
    }

    inline fun <reified T> get(key: String): T? {
        return when (contains(key)) {
            false -> null
            true -> when (T::class) {
                Int::class -> getInt(key)
                Long::class -> getLong(key)
                Float::class -> getFloat(key)
                String::class -> getString(key)
                Boolean::class -> getBoolean(key)
                else -> null
            }
        } as? T
    }

    inline fun <reified T> getLiveData(
        key: String, value: T? = null
    ): LiveData<T> = object : LiveData<T>(value),
        SharedPreferences.OnSharedPreferenceChangeListener {

        private val key = key

        override fun onActive() {
            sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        }

        override fun getValue() = get<T>(key)

        override fun onSharedPreferenceChanged(
            sharedPreferences: SharedPreferences?,
            key: String?
        ) {
            if (this.key == key) {
                setValue(get(key))
            }
        }

        override fun onInactive() {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        }
    }

    inline fun <reified T> getMutableLiveData(
        key: String, value: T? = null
    ): MutableLiveData<T> = object : MutableLiveData<T>(value),
        SharedPreferences.OnSharedPreferenceChangeListener {

        private val key = key

        override fun onActive() {
            sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        }

        override fun getValue() = get<T>(key)

        override fun setValue(value: T) {
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
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        }

    }

    fun getString(key: String) = when (contains(key)) {
        true -> sharedPreferences.getString(key, "")
        false -> null
    }

    fun getStringSet(key: String) = when (contains(key)) {
        true -> sharedPreferences.getStringSet(key, emptySet())
        false -> null
    }

    fun getInt(key: String) = when (contains(key)) {
        true -> sharedPreferences.getInt(key, 0)
        false -> null
    }

    fun getLong(key: String) = when (contains(key)) {
        true -> sharedPreferences.getLong(key, 0L)
        false -> null
    }

    fun getFloat(key: String) = when (contains(key)) {
        true -> sharedPreferences.getFloat(key, 0f)
        false -> null
    }

    fun getBoolean(key: String) = when (contains(key)) {
        true -> sharedPreferences.getBoolean(key, false)
        false -> null
    }

    fun put(pair: Pair<String, Any?>) {
        val key = pair.first.toLowerCase(locale)
        editor.apply {
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

    fun clear() = editor.clear().apply()
    fun remove(key: String) = editor.remove(key).apply()
    fun contains(key: String) = sharedPreferences.contains(key)

}