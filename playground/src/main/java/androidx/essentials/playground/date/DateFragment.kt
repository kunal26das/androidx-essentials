package androidx.essentials.playground.date

import android.os.Bundle
import android.view.View
import androidx.essentials.fragment.Fragment
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.FragmentDateBinding
import androidx.fragment.app.viewModels

class DateFragment : Fragment() {

    override val layout = R.layout.fragment_date
    override val viewModel by viewModels<DateViewModel>()
    override val binding by dataBinding<FragmentDateBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.apply {
            validate.setOnClickListener {
                when {
                    startDate.isInvalid -> startDate.requestFocus()
                    endDate.isInvalid -> endDate.requestFocus()
                }
            }
        }
    }

}
