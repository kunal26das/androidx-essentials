package androidx.essentials.playground.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.essentials.core.ui.Fragment
import androidx.essentials.firebase.utils.UUID
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.FragmentFirebaseBinding
import androidx.essentials.playground.ui.PlayGroundViewModel

class FirebaseFragment : Fragment(true) {

    override val layout = R.layout.fragment_firebase
    override val viewModel by sharedViewModel<PlayGroundViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (binding as FragmentFirebaseBinding).viewModel = viewModel
        (binding as FragmentFirebaseBinding).uuid = UUID
        super.onViewCreated(view, savedInstanceState)
    }

}