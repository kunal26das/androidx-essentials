package androidx.essentials.playground

import android.os.Bundle
import android.view.View
import androidx.essentials.core.Fragment
import androidx.essentials.events.Events
import androidx.essentials.extensions.View.onGlobalLayoutListener
import androidx.essentials.firebase.Firebase
import androidx.essentials.firebase.UUID
import androidx.essentials.location.LocationProvider
import androidx.essentials.network.NetworkCallback
import androidx.essentials.playground.databinding.FragmentPlaygroundBinding
import kotlinx.android.synthetic.main.fragment_playground.*

class PlayGroundFragment : Fragment(true) {

    override val layout = R.layout.fragment_playground
    override val viewModel by sharedViewModel<PlayGroundViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (binding as FragmentPlaygroundBinding).viewModel = viewModel
        root.onGlobalLayoutListener {
            bottomSheetView1.peekHeight = it.height * 4 / 5
        }
        playGroundRecyclerView.submitList(
            listOf(
                "$UUID",
                "${Firebase.TOKEN}",
                "${NetworkCallback.IS_ONLINE}",
                "${LocationProvider.LOCATION}"
            )
        )
    }

    override fun initObservers() {
        viewModel.isOnline.observe {
            Events.publish("$it")
        }
        viewModel.combined?.observe {
            playGroundRecyclerView.submitList((it as MutableList).apply {
                add(0, UUID)
            })
        }
    }

}
