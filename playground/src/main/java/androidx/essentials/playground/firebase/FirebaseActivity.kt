package androidx.essentials.playground.firebase

import android.os.Bundle
import android.view.View
import androidx.essentials.network.Firebase
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.ActivityFirebaseBinding
import androidx.essentials.view.Activity

class FirebaseActivity : Activity() {

    override val layout = R.layout.activity_firebase
    override val binding by dataBinding<ActivityFirebaseBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.firebase = Firebase
    }

}