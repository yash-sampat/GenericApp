package com.generic.login.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

@BindingAdapter("imageUrl")
fun bindURL(imageView: ImageView, url: String) {
    Glide.with(imageView.context)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .centerCrop()
        .into(imageView)
}