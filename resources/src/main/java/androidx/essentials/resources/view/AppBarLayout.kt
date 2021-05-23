package androidx.essentials.resources.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.essentials.extensions.Context.getActivity
import androidx.essentials.resources.R
import androidx.essentials.resources.databinding.LayoutAppbarBinding
import androidx.essentials.resources.view.behaviour.AppBarLayoutBehaviour
import com.google.android.material.card.MaterialCardView

class AppBarLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.materialCardViewStyle
) : MaterialCardView(context, attrs, defStyleAttr) {

    private val inflater = LayoutInflater.from(context)
    private val activity = context.getActivity<AppCompatActivity>()
    private val binding = LayoutAppbarBinding.inflate(inflater, this, true)

    var isLoading: Boolean? = null
        set(value) {
            field = value
            with(binding.contentLoadingProgressBar) {
                when (value) {
                    null, false -> hide()
                    else -> show()
                }
            }
        }

    init {
        binding.contentLoadingProgressBar.hide()
        activity?.setSupportActionBar(binding.toolbar)
        binding.materialAppBarLayout.backgroundTintList = cardBackgroundColor
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        when (layoutParams) {
            is CoordinatorLayout.LayoutParams -> {
                with(layoutParams as CoordinatorLayout.LayoutParams) {
                    behavior = AppBarLayoutBehaviour()
                }
            }
        }
    }

}