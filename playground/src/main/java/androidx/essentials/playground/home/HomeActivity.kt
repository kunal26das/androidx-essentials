package androidx.essentials.playground.home

import android.os.Bundle
import android.view.View
import androidx.essentials.playground.R
import androidx.essentials.playground.databinding.ActivityHomeBinding
import androidx.essentials.view.Activity
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.divider.MaterialDividerItemDecoration.VERTICAL

class HomeActivity : Activity() {

    override val layout = R.layout.activity_home
    override val binding by dataBinding<ActivityHomeBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.libraries.adapter = LibraryAdapter()
        val decoration = MaterialDividerItemDecoration(this, VERTICAL)
        binding.libraries.addItemDecoration(decoration)
    }

}