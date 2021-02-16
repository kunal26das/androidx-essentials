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

    private val binding = LayoutAppbarBinding.inflate(LayoutInflater.from(context), this, true)
    private val contentLoadingProgressBar = binding.contentLoadingProgressBar
    private val appBarLayout = binding.materialAppBarLayout
    val toolbar = binding.toolbar

    var isLoading = DEFAULT_LOADING
        set(value) {
            field = value
            with(contentLoadingProgressBar) {
                if (value) show() else hide()
            }
        }

    init {
        contentLoadingProgressBar.hide()
        appBarLayout.backgroundTintList = cardBackgroundColor
        if (context is Activity) context.setSupportActionBar(toolbar)
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

        private const val DEFAULT_LOADING = false

        @JvmStatic
        @BindingAdapter("loading")
        fun androidx.essentials.core.view.AppBarLayout.setIsLoading(isLoading: Boolean?) {
            this.isLoading = isLoading ?: false
        }

    }

}