package androidx.essentials.playground

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.essentials.core.Fragment
import androidx.essentials.events.Events
import androidx.essentials.playground.databinding.FragmentPlaygroundBinding
import kotlinx.android.synthetic.main.fragment_playground.*

class PlaygroundFragment : Fragment(true) {

    override val layout = R.layout.fragment_playground
    override val viewModel by sharedViewModel<PlayGroundViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (binding as FragmentPlaygroundBinding).viewModel = viewModel
    }

    @SuppressLint("SetTextI18n")
    override fun initObservers() {
        viewModel.isOnline.observe {
            Events.publish("$it")
        }
        viewModel.location.observe {
            location.text = "${it?.latitude}\n${it?.longitude}"
        }
    }

}
