package androidx.essentials.core.preference

import android.content.SharedPreferences
import androidx.essentials.core.injector.KoinComponent.inject

object Preferences : SharedPreferences, SharedPreferences.Editor {

    private val sharedPreferences by inject<SharedPreferences>()

    override fun apply() = edit().apply()
    override fun clear() = edit().clear()!!
    override fun commit() = edit().commit()
    override fun edit() = sharedPreferences.edit()!!
    override fun remove(key: String) = edit().remove(key)!!
    override fun getAll(): MutableMap<String, *> = sharedPreferences.all
    override fun contains(key: String) = sharedPreferences.contains(key)

    /**
     * String
     */

    fun getString(key: String) = if (contains(key)) getString(key, "") else null

    override fun getString(
        key: String?, defValue: String?
    ) = sharedPreferences.getString(key, defValue)

    override fun putString(
        key: String?, value: String?
    ): SharedPreferences.Editor = edit().putString(key, value)

    /**
     * String Set
     */

    fun getStringSet(key: String) = if (contains(key)) getStringSet(key, mutableSetOf()) else null

    override fun getStringSet(
        key: String?, defValues: MutableSet<String>?
    ): MutableSet<String> = sharedPreferences.getStringSet(key, defValues) ?: mutableSetOf()

    override fun putStringSet(
        key: String?, values: MutableSet<String>?
    ): SharedPreferences.Editor = edit().putStringSet(key, values)

    /**
     * Int
     */

    fun getInt(key: String) = if (contains(key)) getInt(key, 0) else null

    override fun getInt(
        key: String?, defValue: Int
    ) = sharedPreferences.getInt(key, defValue)

    override fun putInt(
        key: String?, value: Int
    ): SharedPreferences.Editor = edit().putInt(key, value)

    /**
     * Long
     */

    fun getLong(key: String) = if (contains(key)) getLong(key, 0L) else null

    override fun getLong(
        key: String?, defValue: Long
    ) = sharedPreferences.getLong(key, defValue)

    override fun putLong(
        key: String?, value: Long
    ): SharedPreferences.Editor = edit().putLong(key, value)

    /**
     * Float
     */

    fun getFloat(key: String) = if (contains(key)) getFloat(key, 0f) else null

    override fun getFloat(
        key: String?, defValue: Float
    ) = sharedPreferences.getFloat(key, defValue)

    override fun putFloat(
        key: String?, value: Float
    ): SharedPreferences.Editor = edit().putFloat(key, value)

    /**
     * Boolean
     */

    fun getBoolean(key: String) = if (contains(key)) getBoolean(key, false) else null

    override fun getBoolean(
        key: String?, defValue: Boolean
    ) = sharedPreferences.getBoolean(key, defValue)

    override fun putBoolean(
        key: String?, value: Boolean
    ): SharedPreferences.Editor = edit().putBoolean(key, value)

    /**
     * Listener
     */

    override fun registerOnSharedPreferenceChangeListener(
        listener: SharedPreferences.OnSharedPreferenceChangeListener?
    ) {
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun unregisterOnSharedPreferenceChangeListener(
        listener: SharedPreferences.OnSharedPreferenceChangeListener?
    ) {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
    }

}