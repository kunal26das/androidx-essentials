package androidx.essentials.playground.time

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.ActivityTimeBinding
import androidx.essentials.view.Activity

class TimeActivity : Activity() {

    override val layout = R.layout.activity_time
    private val viewModel by viewModels<TimeViewModel>()
    override val binding by dataBinding<ActivityTimeBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.apply {
            validate.setOnClickListener {
                when {
                    startTime.isInvalid -> startTime.requestFocus()
                    endTime.isInvalid -> endTime.requestFocus()
                }
            }
        }
    }

}
