package androidx.essentials.firebase

import android.content.Context
import androidx.essentials.core.injector.KoinComponent.inject
import androidx.essentials.preferences.SharedPreferences
import androidx.lifecycle.LiveData
import com.google.firebase.messaging.ktx.messaging

object Firebase {

    var TOKEN: String? = null
        internal set(value) {
            field = value?.apply {
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
            with(sharedPreferences.getString(KEY_UUID)) {
                return when {
                    this.isNullOrBlank() -> "${java.util.UUID.randomUUID()}".apply { UUID = this }
                    else -> this
                }
            }
        }
        internal set(value) = sharedPreferences.put(Pair(KEY_UUID, value))

    private const val KEY_UUID = "uuid"
    private val applicationContext by inject<Context>()
    private val onTokenChangeListeners by lazy { mutableListOf<(String) -> Unit>() }

    private val sharedPreferences by lazy {
        SharedPreferences(applicationContext, javaClass.simpleName)
    }

    init {
        com.google.firebase.ktx.Firebase.messaging.token.addOnSuccessListener { TOKEN = it }
    }

    fun addOnTokenChangeListener(onTokenChangeListener: (String) -> Unit) {
        onTokenChangeListeners.add(onTokenChangeListener)
    }

    fun removeOnTokenChangeListener(onTokenChangeListener: (String) -> Unit) {
        onTokenChangeListeners.remove(onTokenChangeListener)
    }

}