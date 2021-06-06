package com.example.mywallet.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.mywallet.R

@BindingAdapter("avatar")
fun setAvatar(v: ImageView, url: String?) {
    if (url == null)
        v.setImageResource(R.drawable.ic_currency_placeholder)
    else
        v.loadAvatar(url)
}

