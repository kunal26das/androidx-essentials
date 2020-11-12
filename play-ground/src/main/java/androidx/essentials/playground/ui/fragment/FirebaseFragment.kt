package androidx.essentials.playground.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.essentials.core.lifecycle.Fragment
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.FragmentFirebaseBinding
import androidx.essentials.playground.ui.PlayGroundViewModel

class FirebaseFragment : Fragment<FragmentFirebaseBinding>() {

    override val layout = R.layout.fragment_firebase
    override val sharedViewModel by sharedViewModel<PlayGroundViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.sharedViewModel = sharedViewModel
    }

}