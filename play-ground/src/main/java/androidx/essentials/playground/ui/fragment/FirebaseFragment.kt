package androidx.essentials.playground.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.essentials.core.injector.KoinComponent.inject
import androidx.essentials.core.ui.Fragment
import androidx.essentials.firebase.utils.UUID
import androidx.essentials.playground.R
import androidx.essentials.playground.ui.PlayGroundViewModel
import kotlinx.android.synthetic.main.fragment_firebase.*

class FirebaseFragment : Fragment() {

    override val layout = R.layout.fragment_firebase
    override val viewModel by inject<PlayGroundViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uuid.subtitle.text = UUID.toString()
        uuid.title.text = getString(R.string.uuid)
    }

    override fun initObservers() {
        super.initObservers()
        viewModel.token.observe {
            token.subtitle.text = it
            token.title.text = getString(R.string.token)
        }
    }
}