package androidx.essentials.firebase.utils

import android.content.SharedPreferences
import androidx.essentials.core.KoinComponent.inject
import java.util.UUID

object UUID {

    private const val KEY_UUID = "UUID"
    private val sharedPreferences: SharedPreferences by inject()

    init {
        if (!sharedPreferences.contains(
                KEY_UUID
            )) {
            with(sharedPreferences.edit()) {
                putString(KEY_UUID, UUID.randomUUID().toString())
                apply()
            }
        }
    }

    override fun toString(): String {
        return sharedPreferences.getString(
            KEY_UUID, "") ?: ""
    }

}