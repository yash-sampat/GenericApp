package com.generic.login.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun bindURL(imageView: ImageView, url: String) {
    Glide.with(imageView.context)
        .load(url)
        .centerCrop()
        .into(imageView)
}