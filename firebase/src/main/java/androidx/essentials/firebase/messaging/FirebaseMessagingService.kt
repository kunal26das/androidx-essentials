package androidx.essentials.firebase.messaging

import android.util.Log
import androidx.essentials.firebase.Firebase
import androidx.essentials.preferences.SharedPreferences
import androidx.essentials.preferences.SharedPreferences.Companion.put
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

open class FirebaseMessagingService : FirebaseMessagingService(), SharedPreferences {

    override fun onDeletedMessages() {
        log(Event.ON_DELETED_MESSAGES)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        log(Event.ON_MESSAGE_RECEIVED)
    }

    override fun onMessageSent(string: String) {
        log(Event.ON_MESSAGE_SENT)
    }

    override fun onNewToken(token: String) {
        put(Pair(Firebase.Preference.TOKEN, token))
        log(Event.ON_NEW_TOKEN)
    }

    override fun onSendError(string: String, e: Exception) {
        log(Event.ON_SEND_ERROR)
    }

    private fun log(event: Event) {
        Log.d(javaClass.simpleName, event.name)
    }

    companion object {
        private enum class Event {
            ON_DELETED_MESSAGES,
            ON_MESSAGE_RECEIVED,
            ON_MESSAGE_SENT,
            ON_NEW_TOKEN,
            ON_SEND_ERROR,
        }
    }

}