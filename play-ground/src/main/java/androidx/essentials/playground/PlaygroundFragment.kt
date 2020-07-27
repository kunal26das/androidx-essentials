package androidx.essentials.playground

import android.os.Bundle
import android.view.View
import androidx.essentials.core.Fragment
import androidx.essentials.events.Events
import androidx.essentials.playground.databinding.FragmentPlaygroundBinding
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_playground.*

class PlaygroundFragment : Fragment(true) {

    override val layout = R.layout.fragment_playground
    override val viewModel by sharedViewModel<PlayGroundViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (binding as FragmentPlaygroundBinding).viewModel = viewModel
    }

    override fun initObservers() {
        viewModel.isOnline.observe(this, Observer {
            networkState.text = when (it) {
                true -> {
                    bottomSheetView.expand()
                    getString(R.string.online)
                }
                false -> {
                    bottomSheetView.collapse()
                    getString(R.string.offline)
                }
            }
            Events.publish(networkState.text)
        })
    }

}
