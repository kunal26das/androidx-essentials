package androidx.essentials.playground.repository

import android.os.Bundle
import android.view.View
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.FragmentRepositoryBinding
import androidx.essentials.ui.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class RepositoryFragment : Fragment() {

    override val layout = R.layout.fragment_repository
    private val viewModel by viewModels<RepositoryViewModel>()
    override val binding by dataBinding<FragmentRepositoryBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.pager.observe {
            lifecycleScope.launch {
                binding.pagedLibraries.submitList(it)
            }
        }
    }

}