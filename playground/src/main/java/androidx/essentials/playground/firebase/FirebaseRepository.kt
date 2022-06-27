package androidx.essentials.playground.firebase

import javax.inject.Inject

class FirebaseRepository @Inject constructor(
    private val firebaseService: FirebaseService,
) {

    suspend fun getToken() = firebaseService.getToken()

}