package androidx.essentials.core.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Build
import androidx.essentials.core.BuildConfig
import androidx.essentials.extensions.TryCatch.Try
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import java.util.*

@Suppress("UNCHECKED_CAST")
class SharedPreferences(
    context: Context,
    name: String = context.packageName
) {

    val ALL get() = sharedPreferences.all
    private val locale by lazy { Locale.getDefault() }
    private val editor get() = sharedPreferences.edit()

    private val sharedPreferences by lazy {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !BuildConfig.DEBUG -> {
                EncryptedSharedPreferences.create(
                    name.toLowerCase(locale),
                    MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC), context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )
            }
            else -> context.getSharedPreferences(name, MODE_PRIVATE)!!
        }
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

    fun <T> get(key: String): T? {
        when (contains(key)) {
            false -> return null
            true -> {
                repeat(5) {
                    Try {
                        return when (it) {
                            0 -> getInt(key)
                            1 -> getLong(key)
                            2 -> getFloat(key)
                            3 -> getBoolean(key)
                            else -> getString(key)
                        } as? T
                    }
                }
                return null
            }
        }
    }

    fun <T> getLiveData(
        key: String, value: T? = null
    ): LiveData<T> = object : LiveData<T>(value),
        SharedPreferences.OnSharedPreferenceChangeListener {

        private val key = key

        override fun onActive() {
            super.onActive()
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
            super.onInactive()
        }
    }

    fun <T> getMutableLiveData(
        key: String, value: T? = null
    ): MutableLiveData<T> = object : MutableLiveData<T>(value),
        SharedPreferences.OnSharedPreferenceChangeListener {

        private val key = key

        override fun onActive() {
            super.onActive()
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
            super.onInactive()
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
                        is Boolean -> putBoolean(key, this)
                        else -> putString(key, "$this")
                    }
                }
            }
        }.apply()
    }

    fun clear() = editor.clear().apply()
    fun remove(key: String) = editor.remove(key).apply()
    fun contains(key: String) = sharedPreferences.contains(key)

}