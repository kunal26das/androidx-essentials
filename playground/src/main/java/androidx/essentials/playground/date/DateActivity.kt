package androidx.essentials.playground.date

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.ActivityDateBinding
import androidx.essentials.view.Activity

class DateActivity : Activity() {

    override val layout = R.layout.activity_date
    private val viewModel by viewModels<DateViewModel>()
    override val binding by dataBinding<ActivityDateBinding>()

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
