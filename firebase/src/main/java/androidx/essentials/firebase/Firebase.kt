package androidx.essentials.firebase

import androidx.essentials.core.injector.KoinComponent.inject
import com.google.firebase.iid.FirebaseInstanceId

class Firebase private constructor() {

    var token: String? = null
        internal set(value) {
            field = value
            onTokenChangeListener?.onNewToken(value)
        }

    private var onTokenChangeListener: OnTokenChangeListener? = null

    fun setOnTokenChangeListener(onTokenChangeListener: OnTokenChangeListener) {
        this.onTokenChangeListener = onTokenChangeListener
    }

    fun setOnTokenChangeListener(action: (String?) -> Unit) {
        setOnTokenChangeListener(object : OnTokenChangeListener {
            override fun onNewToken(token: String?) {
                action(token)
            }
        })
    }

    fun removeOnTokenChangeListener() {
        onTokenChangeListener = null
    }

    interface OnTokenChangeListener {
        fun onNewToken(token: String?)
    }

    companion object {

        private var firebase: Firebase? = null
        private val firebaseInstanceId: FirebaseInstanceId by inject()

        fun getInstance(): Firebase {
            if (firebase != null) {
                return firebase!!
            }
            synchronized(this) {
                firebase = Firebase()
                firebaseInstanceId.instanceId.addOnSuccessListener {
                    firebase?.token = it.token
                }
                return firebase!!
            }
        }
    }
}