package androidx.essentials.playground.ui.time

import android.os.Bundle
import android.view.View
import androidx.essentials.core.lifecycle.owner.Fragment
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.FragmentTimeBinding
import androidx.fragment.app.viewModels

class TimeFragment : Fragment() {

    override val layout = R.layout.fragment_time
    override val viewModel by viewModels<TimeViewModel>()
    override val binding by dataBinding<FragmentTimeBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.apply {
            validate.setOnClickListener {
                when {
                    startTime.isInvalid -> startTime.requestFocus()
                    endTime.isInvalid -> endTime.requestFocus()
                    else -> toast(R.string._true)
                }
            }
        }
    }

}
