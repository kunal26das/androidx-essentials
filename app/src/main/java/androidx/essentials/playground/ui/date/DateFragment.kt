package androidx.essentials.playground.ui.date

import android.os.Bundle
import android.view.View
import androidx.essentials.core.lifecycle.owner.Fragment
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.FragmentDateBinding

class DateFragment : Fragment() {

    override val layout = R.layout.fragment_date
    override val viewModel by viewModel<DateViewModel>()
    override val binding by dataBinding<FragmentDateBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.apply {
            validate.setOnClickListener {
                when {
                    startDate.isInvalid -> startDate.requestFocus()
                    endDate.isInvalid -> endDate.requestFocus()
                    else -> toast(R.string._true)
                }
            }
        }
    }

}
