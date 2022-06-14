package androidx.essentials.network.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.content.SharedPreferences as AndroidSharedPreferences

object SharedPreferences : AndroidSharedPreferences, AndroidSharedPreferences.Editor {

    override fun apply() = edit().apply()
    override fun clear() = edit().clear()!!
    override fun commit() = edit().commit()
    override fun remove(key: String) = edit().remove(key)!!
    override fun getAll() = sharedPreferences.all ?: emptyMap()
    override fun contains(key: String) = sharedPreferences.contains(key)
    override fun edit(): AndroidSharedPreferences.Editor = sharedPreferences.edit()!!

    private lateinit var sharedPreferences: AndroidSharedPreferences

    private val Context.sharedPreferences: AndroidSharedPreferences
        get() = getSharedPreferences(packageName, MODE_PRIVATE)

    fun init(context: Context) {
        synchronized(context.applicationContext) {
            sharedPreferences = context.sharedPreferences
        }
    }

    /**
     * String
     */

    fun getString(key: String) = if (contains(key)) getString(key, null) else null

    override fun getString(
        key: String?, defValue: String?
    ) = sharedPreferences.getString(key, defValue) ?: defValue

    override fun putString(
        key: String?, value: String?
    ) = edit().putString(key, value)!!

    /**
     * String Set
     */

    fun getStringSet(key: String) = if (contains(key)) getStringSet(key, null) else null

    override fun getStringSet(
        key: String?, defValues: Set<String>?
    ) = sharedPreferences.getStringSet(key, defValues) ?: defValues

    override fun putStringSet(
        key: String?, values: Set<String>?
    ) = edit().putStringSet(key, values)!!

    /**
     * Int
     */

    fun getInt(key: String) = if (contains(key)) getInt(key, 0) else null

    override fun getInt(
        key: String?, defValue: Int
    ) = sharedPreferences.getInt(key, defValue)

    override fun putInt(
        key: String?, value: Int
    ) = edit().putInt(key, value)!!

    /**
     * Long
     */

    fun getLong(key: String) = if (contains(key)) getLong(key, 0L) else null

    override fun getLong(
        key: String?, defValue: Long
    ) = sharedPreferences.getLong(key, defValue)

    override fun putLong(
        key: String?, value: Long
    ) = edit().putLong(key, value)!!

    /**
     * Float
     */

    fun getFloat(key: String) = if (contains(key)) getFloat(key, 0f) else null

    override fun getFloat(
        key: String?, defValue: Float
    ) = sharedPreferences.getFloat(key, defValue)

    override fun putFloat(
        key: String?, value: Float
    ) = edit().putFloat(key, value)!!

    /**
     * Boolean
     */

    fun getBoolean(key: String) = if (contains(key)) getBoolean(key, false) else null

    override fun getBoolean(
        key: String?, defValue: Boolean
    ) = sharedPreferences.getBoolean(key, defValue)

    override fun putBoolean(
        key: String?, value: Boolean
    ) = edit().putBoolean(key, value)!!

    /**
     * Listener
     */

    override fun registerOnSharedPreferenceChangeListener(
        listener: AndroidSharedPreferences.OnSharedPreferenceChangeListener?
    ) = sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

    override fun unregisterOnSharedPreferenceChangeListener(
        listener: AndroidSharedPreferences.OnSharedPreferenceChangeListener?
    ) = sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)

    inline fun <reified T> Any.get(key: Enum<*>): T? {
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

    fun Any.put(key: Enum<*>, value: Any?) {
        edit().apply {
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

    inline fun <reified T> Any.liveData(
        key: Enum<*>
    ): Lazy<LiveData<T?>> = lazy {
        object : LiveData<T?>(), AndroidSharedPreferences.OnSharedPreferenceChangeListener {

            private val key = key

            override fun onActive() {
                registerOnSharedPreferenceChangeListener(this)
            }

            override fun getValue() = get<T>(key)

            override fun onSharedPreferenceChanged(
                sharedPreferences: AndroidSharedPreferences?,
                key: String?
            ) {
                if (this.key.name == key) {
                    setValue(get<T>(this.key))
                }
            }

            override fun onInactive() {
                unregisterOnSharedPreferenceChangeListener(this)
            }
        }
    }

    inline fun <reified T> Any.mutableLiveData(
        key: Enum<*>
    ): Lazy<MutableLiveData<T?>> = lazy {
        object : MutableLiveData<T?>(),
            AndroidSharedPreferences.OnSharedPreferenceChangeListener {

            private val key = key

            override fun onActive() {
                registerOnSharedPreferenceChangeListener(this)
            }

            override fun getValue() = get<T>(key)

            override fun setValue(value: T?) {
                if (value != getValue()) {
                    super.setValue(value)
                    put(key, value)
                }
            }

            override fun onSharedPreferenceChanged(
                sharedPreferences: AndroidSharedPreferences?,
                key: String?
            ) {
                if (this.key.name == key) {
                    value = get<T>(this.key)
                }
            }

            override fun onInactive() {
                unregisterOnSharedPreferenceChangeListener(this)
            }

        }
    }

}