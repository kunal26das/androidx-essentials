package androidx.essentials.playground.firebase

import android.os.Bundle
import android.view.View
import androidx.essentials.network.Firebase
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.FragmentFirebaseBinding
import androidx.essentials.view.Fragment

class FirebaseFragment : Fragment() {

    override val layout = R.layout.fragment_firebase
    override val binding by dataBinding<FragmentFirebaseBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.firebase = Firebase
    }

}