package androidx.essentials.playground

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.essentials.core.Fragment
import androidx.essentials.events.Events
import androidx.essentials.extensions.View.onGlobalLayoutListener
import androidx.essentials.firebase.UUID
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
            bottomSheetView2.peekHeight = it.height * 3 / 5
            bottomSheetView3.peekHeight = it.height * 2 / 5
            bottomSheetView4.peekHeight = it.height * 1 / 5
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initObservers() {
        uuid.text = UUID.toString()
        viewModel.token.observe {
            token.text = it
        }
        viewModel.isOnline.observe {
            isOnline.text = "$it"
            Events.publish("$it")
        }
        viewModel.location.observe {
            location.text = "${it?.latitude}\n${it?.longitude}"
        }
    }

}
