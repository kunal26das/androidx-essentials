package androidx.essentials.playground.ui.firebase

import androidx.essentials.core.lifecycle.observer.ViewModel
import androidx.essentials.firebase.Firebase

class FirebaseViewModel : ViewModel() {

    val uuid = Firebase.UUID
    val token = Firebase.token

}