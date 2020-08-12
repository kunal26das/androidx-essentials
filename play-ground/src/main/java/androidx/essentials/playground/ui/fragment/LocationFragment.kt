package androidx.essentials.playground.ui.fragment

import android.annotation.SuppressLint
import androidx.essentials.core.ui.Fragment
import androidx.essentials.playground.R
import androidx.essentials.playground.ui.PlayGroundViewModel
import kotlinx.android.synthetic.main.fragment_location.*

class LocationFragment : Fragment(true) {

    override val layout = R.layout.fragment_location
    override val viewModel by sharedViewModel<PlayGroundViewModel>()

    @SuppressLint("SetTextI18n")
    override fun initObservers() {
        viewModel.location.observe {
            location.title.text = getString(R.string.location)
            location.subtitle.text = "${it?.latitude}, ${it?.longitude}"
        }
    }

}
