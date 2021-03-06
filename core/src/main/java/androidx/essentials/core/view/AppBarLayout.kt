package androidx.essentials.core.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.BindingAdapter
import androidx.essentials.core.R
import androidx.essentials.core.databinding.LayoutAppbarBinding
import androidx.essentials.core.lifecycle.owner.Activity
import com.google.android.material.card.MaterialCardView

class AppBarLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.materialCardViewStyle
) : MaterialCardView(context, attrs, defStyleAttr) {

    private val inflater = LayoutInflater.from(context)
    private val binding = LayoutAppbarBinding.inflate(inflater, this, true)
    val toolbar = binding.toolbar

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
        if (context is Activity) context.setSupportActionBar(toolbar)
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

    companion object {

        @JvmStatic
        @BindingAdapter("loading")
        fun AppBarLayout.setIsLoading(isLoading: Boolean?) {
            this.isLoading = isLoading
        }

        @JvmStatic
        @BindingAdapter("title")
        fun AppBarLayout.setTitle(title: String?) {
            this.toolbar.title = title
        }

        @JvmStatic
        @BindingAdapter("subTitle")
        fun AppBarLayout.setSubTitle(subTitle: String?) {
            this.toolbar.subtitle = subTitle
        }

    }

}