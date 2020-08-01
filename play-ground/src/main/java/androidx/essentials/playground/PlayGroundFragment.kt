package androidx.essentials.playground

import android.os.Bundle
import android.view.View
import androidx.essentials.core.Fragment
import androidx.essentials.events.Events
import androidx.essentials.playground.databinding.FragmentPlayGroundBinding
import kotlinx.android.synthetic.main.fragment_play_ground.*

class PlayGroundFragment : Fragment(true) {

    override val layout = R.layout.fragment_play_ground
    override val viewModel by sharedViewModel<PlayGroundViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (binding as FragmentPlayGroundBinding).viewModel = viewModel
    }

    override fun initObservers() {
        viewModel.isOnline.observe {
            Events.publish("$it")
        }
        viewModel.dummy.observe {
            playGroundRecyclerView.submitList(it)
        }
        viewModel.appBarLayoutHeight.observe {
            if (it > 0) {
                playGroundRecyclerView.marginVertical = it
                playGroundRecyclerView.visibility = View.VISIBLE
            }
        }
    }

}
