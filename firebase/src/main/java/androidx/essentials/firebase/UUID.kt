package androidx.essentials.firebase

import android.content.SharedPreferences
import androidx.essentials.core.KoinComponent.inject
import java.util.UUID

object UUID {

    private const val KEY_UUID = "UUID"
    private val sharedPreferences: SharedPreferences by inject()
    override fun toString(): String {
        return sharedPreferences.getString(KEY_UUID, "") ?: ""
    }

    init {
        if (!sharedPreferences.contains(KEY_UUID)) {
            with(sharedPreferences.edit()) {
                putString(KEY_UUID, UUID.randomUUID().toString())
                apply()
            }
        }
    }

}