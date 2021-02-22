package androidx.essentials.preferences

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class EncryptedSharedPreferences(
    context: Context,
    name: String = context.packageName
) : SharedPreferences(context, name) {

    override val sharedPreferences by lazy {
        EncryptedSharedPreferences.create(
            context, name.toLowerCase(locale),
            MasterKey.Builder(context).apply {
                setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            }.build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

}