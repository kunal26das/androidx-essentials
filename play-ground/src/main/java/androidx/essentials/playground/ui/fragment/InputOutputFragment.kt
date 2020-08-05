package androidx.essentials.playground.ui.fragment

import android.os.Bundle
import android.view.MenuInflater
import android.view.View
import android.widget.PopupMenu
import androidx.core.view.children
import androidx.essentials.core.Fragment
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.FragmentInputOutputBinding
import androidx.essentials.playground.ui.PlayGroundViewModel
import kotlinx.android.synthetic.main.fragment_input_output.*

class InputOutputFragment : Fragment(true) {

    override val layout = R.layout.fragment_input_output
    override val viewModel by sharedViewModel<PlayGroundViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (binding as FragmentInputOutputBinding).viewModel = viewModel
        PopupMenu(context, null).apply {
            MenuInflater(context).inflate(R.menu.menu_play_ground, menu)
            autoComplete.array = menu.children.map {
                "${it.title}"
            }.toList().toTypedArray()
        }
    }

}
