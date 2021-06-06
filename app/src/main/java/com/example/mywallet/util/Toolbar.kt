package com.example.mywallet.util

import android.os.Build
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.mywallet.R
import com.google.android.material.appbar.AppBarLayout

fun AppCompatActivity.addToolbar(
    @LayoutRes toolbarLayoutId: Int,
    viewGroup: ViewGroup?,
    toolbarCallBack: ((activity: AppCompatActivity?, toolbar: Toolbar?) -> Unit)? = null
) {
    viewGroup?.findViewById<AppBarLayout>(R.id.appBarLayout)?.apply {
        viewGroup.removeView(this)
    }

    val toolbarItem = layoutInflater.inflate(toolbarLayoutId, viewGroup, false) ?: return
    toolbarItem.stateListAnimator = null
    viewGroup?.addView(toolbarItem)

    val toolbar = toolbarItem.findViewById<Toolbar>(R.id.toolbar)
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayShowTitleEnabled(false)
    toolbarCallBack?.invoke(this, toolbar)
}

fun AppCompatActivity.removeToolbar(viewGroup: ViewGroup?) {
    viewGroup?.findViewById<AppBarLayout>(R.id.appBarLayout)?.apply {
        viewGroup.removeView(this)
    }
}