package androidx.essentials.firebase

import android.content.Context
import androidx.essentials.core.injector.KoinComponent.inject
import androidx.essentials.preferences.SharedPreferences
import androidx.essentials.preferences.SharedPreferences.Companion.get
import androidx.essentials.preferences.SharedPreferences.Companion.put
import androidx.lifecycle.LiveData
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

object Firebase : SharedPreferences {

    private val context by inject<Context>()

    var TOKEN = get<String>(Preference.TOKEN)
        internal set(value) {
            field = value?.apply {
                put(Pair(Preference.TOKEN, value))
                onTokenChangeListeners.forEach {
                    it.invoke(this)
                }
            }
        }

    val token: LiveData<String> by lazy {
        object : LiveData<String>(TOKEN) {

            val onTokenChangeListener = { token: String -> value = token }

            override fun onActive() {
                super.onActive()
                addOnTokenChangeListener(onTokenChangeListener)
            }

            override fun getValue() = TOKEN

            override fun onInactive() {
                removeOnTokenChangeListener(onTokenChangeListener)
                super.onInactive()
            }
        }
    }

    var UUID: String
        get() {
            with(get<String>(Preference.UUID)) {
                return when {
                    isNullOrEmpty() -> "${java.util.UUID.randomUUID()}".apply { UUID = this }
                    else -> this
                }
            }
        }
        internal set(value) = put(Pair(Preference.UUID, value))

    private val onTokenChangeListeners by lazy { mutableListOf<(String) -> Unit>() }

    init {
        Firebase.messaging.token.addOnSuccessListener { TOKEN = it }
    }

    fun addOnTokenChangeListener(onTokenChangeListener: (String) -> Unit) {
        onTokenChangeListeners.add(onTokenChangeListener)
    }

    fun removeOnTokenChangeListener(onTokenChangeListener: (String) -> Unit) {
        onTokenChangeListeners.remove(onTokenChangeListener)
    }

    private enum class Preference {
        TOKEN, UUID
    }

}