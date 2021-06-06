package androidx.essentials.playground.repository

import androidx.essentials.fragment.Fragment
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.FragmentRepositoryBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class RepositoryFragment : Fragment() {

    override val layout = R.layout.fragment_repository
    override val viewModel by viewModels<RepositoryViewModel>()
    override val binding by dataBinding<FragmentRepositoryBinding>()

    override fun initObservers() {
        super.initObservers()
        viewModel.pager.observe {
            lifecycleScope.launch {
                binding.pagedLibraries.submitList(it)
            }
        }
    }

}