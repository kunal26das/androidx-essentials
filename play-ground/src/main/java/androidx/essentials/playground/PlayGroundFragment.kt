package androidx.essentials.playground

import android.os.Bundle
import android.view.View
import androidx.essentials.core.Fragment
import androidx.essentials.core.Resources.statusBarHeight
import androidx.essentials.events.Events
import androidx.essentials.extensions.View.addOnGlobalLayoutListener
import androidx.essentials.playground.databinding.FragmentPlayGroundBinding
import kotlinx.android.synthetic.main.fragment_play_ground.*

class PlayGroundFragment : Fragment(true) {

    override val layout = R.layout.fragment_play_ground
    override val viewModel by sharedViewModel<PlayGroundViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        root.addOnGlobalLayoutListener {
            bottomSheetView.peekHeight = it.height / 2
        }
        statusBarMask.layoutParams.height = statusBarHeight
        (binding as FragmentPlayGroundBinding).viewModel = viewModel
    }

    override fun initObservers() {
        viewModel.isOnline.observe {
            Events.publish("$it")
        }
        viewModel.combined.observe {
            playGroundRecyclerView.submitList(it)
        }
    }

}
