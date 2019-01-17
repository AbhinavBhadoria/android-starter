package bhadoria.abhinav.presentation.utils

import android.content.res.ColorStateList
import android.graphics.Color
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import bhadoria.abhinav.domain.model.PullRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String) {
    Glide.with(imageView.context)
            .load(url)
            .apply(RequestOptions.circleCropTransform())
            .into(imageView)
}

@BindingAdapter("pullRequest")
fun setImageUrl(textView: TextView, pullRequest: PullRequest) {
    textView.text = "#${pullRequest.number} ${pullRequest.state} ${pullRequest.getDaysSinceCreated()} days ago"
}

@BindingAdapter("pullRequestLabels")
fun setChips(chipGroup: ChipGroup, pullRequest: PullRequest) {  
    chipGroup.removeAllViews()
    pullRequest.labels?.forEach {
        val chip = Chip(chipGroup.context)
        chip.text = it.name

        val themeColorStateList = ColorStateList(
                arrayOf(intArrayOf()
                ),
                intArrayOf(Color.parseColor("#" + it.color))
        )
        chip.chipBackgroundColor = themeColorStateList
        chip.isClickable = false

        chipGroup.addView(chip)
    }
}

@BindingAdapter("offlineLogoVisibility")
fun setVisibility(view: View, pullRequest: PullRequest) {
    view.visibility = if (pullRequest.isOfflineResult) View.VISIBLE else View.GONE
}

@BindingAdapter("htmlText")
fun setHtmlText(view: TextView, pullRequest: PullRequest) {
    pullRequest.body?.run {
        view.text = Html.fromHtml(this)
    }
}
