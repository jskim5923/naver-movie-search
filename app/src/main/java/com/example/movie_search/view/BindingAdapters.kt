package com.example.movie_search.view

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide



class BindingAdapters {
    companion object {
        @BindingAdapter(value = ["imageUrl", "error"], requireAll = false)
        @JvmStatic
        fun loadImage(imageView: ImageView, imageUrl: String, placeHolder: Drawable) {
            Glide.with(imageView.context)
                .load(imageUrl)
                .placeholder(placeHolder)
                .centerCrop()
                .into(imageView)
        }
    }
}