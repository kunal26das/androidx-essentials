package androidx.essentials.firebase

import androidx.essentials.core.KoinComponent.inject
import com.google.firebase.iid.FirebaseInstanceId

class Firebase private constructor() {

    fun setOnTokenChangeListener(onTokenChangeListener: OnTokenChangeListener) {
        Companion.onTokenChangeListener = onTokenChangeListener
    }

    fun setOnTokenChangeListener(action: (String?) -> Unit) {
        onTokenChangeListener = object : OnTokenChangeListener {
            override fun onNewToken(token: String?) {
                action(token)
            }
        }
    }

    fun removeListener() {
        onTokenChangeListener = null
    }

    interface OnTokenChangeListener {
        fun onNewToken(token: String?)
    }

    companion object {

        var TOKEN: String? = null
            set(value) {
                field = value
                onTokenChangeListener?.onNewToken(value)
            }

        private val firebaseInstanceId: FirebaseInstanceId by inject()
        private var onTokenChangeListener: OnTokenChangeListener? = null

        init {
            synchronized(this) {
                firebaseInstanceId.instanceId.addOnSuccessListener {
                    TOKEN = it.token
                }
            }
        }

        fun getInstance() = Firebase()
    }
}