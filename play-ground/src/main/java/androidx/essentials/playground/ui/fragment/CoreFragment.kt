package androidx.essentials.playground.ui.fragment

import android.os.Bundle
import android.util.TypedValue
import android.view.MenuInflater
import android.view.View
import android.widget.PopupMenu
import androidx.core.view.children
import androidx.essentials.core.Fragment
import androidx.essentials.extensions.Try.Try
import androidx.essentials.playground.R
import androidx.essentials.playground.ui.PlayGroundViewModel
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_core.*

class CoreFragment : Fragment() {

    override val layout = R.layout.fragment_core
    override val viewModel by sharedViewModel<PlayGroundViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLibraries()
        val typedValue = TypedValue()
        if (requireActivity().theme.resolveAttribute(
                android.R.attr.actionBarSize,
                typedValue,
                true
            )
        ) {
            val actionBarSize =
                TypedValue.complexToDimensionPixelSize(typedValue.data, resources.displayMetrics)
            val appBarVerticalMargin =
                requireContext().resources.getDimension(R.dimen.margin_app_bar_vertical)
            libraryList.marginVertical = (actionBarSize + appBarVerticalMargin).toInt()
        }
    }

    private fun initLibraries() {
        PopupMenu(context, null).apply {
            MenuInflater(context).inflate(R.menu.menu_play_ground, menu)
            libraryList.submitList(menu.children.toList())
            libraryList.setOnItemClickListener {
                if (it.itemId != R.id.core) Try {
                    findNavController().navigate(it.itemId)
                }
            }
        }
    }
}