package androidx.essentials.playground.firebase

import android.os.Bundle
import android.view.View
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.ActivityFirebaseBinding
import androidx.essentials.view.Activity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FirebaseActivity : Activity() {

    @Inject
    lateinit var firebase: Firebase
    override val layout = R.layout.activity_firebase
    override val binding by dataBinding<ActivityFirebaseBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.firebase = firebase
    }

}