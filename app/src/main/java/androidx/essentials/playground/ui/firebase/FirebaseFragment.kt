package androidx.essentials.playground.ui.firebase

import android.os.Bundle
import android.view.View
import androidx.essentials.core.lifecycle.owner.Fragment
import androidx.essentials.firebase.Firebase
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.FragmentFirebaseBinding

class FirebaseFragment : Fragment() {

    override val layout = R.layout.fragment_firebase
    override val binding by dataBinding<FragmentFirebaseBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.firebase = Firebase
    }

}