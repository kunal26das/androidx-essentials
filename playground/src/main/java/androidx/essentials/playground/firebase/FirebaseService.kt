package androidx.essentials.playground.firebase

import com.google.android.gms.tasks.Tasks
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

interface FirebaseService {

    suspend fun getToken(): String? {
        val token = Firebase.messaging.token
        return Tasks.await(token)
    }

    companion object {
        fun getInstance(): FirebaseService {
            return object : FirebaseService {}
        }
    }

}