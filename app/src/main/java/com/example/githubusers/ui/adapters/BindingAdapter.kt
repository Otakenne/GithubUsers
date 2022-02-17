package com.example.githubusers.ui.adapters

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import coil.load

/**
 * Binding adapter method to load images with the coil library
 * @author Otakenne
 */
@BindingAdapter("imageUrl")
fun loadImage(
    imageView: ImageView,
    imageUrl: String?
) {
    imageUrl?.let {
        imageView.load(imageUrl) {}
    }
}

/**
 * Binding adapter method to clip the edges of a linearlayout, I use them to simulate rounded images
 * @author Otakenne
 */
@BindingAdapter("clipToOutline")
fun clipToOutline(
    linearLayout: LinearLayout,
    clip: Boolean?
) {
    clip?.let {
        linearLayout.clipToOutline = clip
    }
}

/**
 * Binding adapter method to toggle view's visibility
 * @author Otakenne
 */
@BindingAdapter("visibility")
fun isVisible(view: View, visible: Boolean) {
    when (visible) {
        true -> view.visibility = View.VISIBLE
        else -> view.visibility = View.GONE
    }
}