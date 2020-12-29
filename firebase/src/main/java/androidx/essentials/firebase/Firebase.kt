package androidx.essentials.firebase

import com.google.firebase.messaging.ktx.messaging

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

        fun getInstance(): Firebase {
            if (firebase != null) {
                return firebase!!
            }
            synchronized(this) {
                firebase = Firebase()
                com.google.firebase.ktx.Firebase.messaging.token.addOnSuccessListener {
                    firebase?.token = it
                }
                return firebase!!
            }
        }
    }
}