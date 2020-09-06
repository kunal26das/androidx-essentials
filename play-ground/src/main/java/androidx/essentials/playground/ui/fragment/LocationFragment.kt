package androidx.essentials.playground.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.essentials.core.ui.Fragment
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.FragmentLocationBinding
import androidx.essentials.playground.ui.PlayGroundViewModel

class LocationFragment : Fragment(true) {

    override val layout = R.layout.fragment_location
    override val viewModel by sharedViewModel<PlayGroundViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (binding as FragmentLocationBinding).viewModel = viewModel
        super.onViewCreated(view, savedInstanceState)
    }

}
