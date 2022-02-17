package com.example.githubusers.ui.adapters

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.githubusers.model.GithubUser

@BindingAdapter("imageUrl")
fun loadImage(
    imageView: ImageView,
    imageUrl: String?
) {
    imageUrl?.let {
        imageView.load(imageUrl) {}
    }
}

@BindingAdapter("clipToOutline")
fun clipToOutline(
    linearLayout: LinearLayout,
    clip: Boolean?
) {
    clip?.let {
        linearLayout.clipToOutline = clip
    }
}

@BindingAdapter("visibility")
fun isVisible(view: View, visible: Boolean) {
    when (visible) {
        true -> view.visibility = View.VISIBLE
        else -> view.visibility = View.GONE
    }
}