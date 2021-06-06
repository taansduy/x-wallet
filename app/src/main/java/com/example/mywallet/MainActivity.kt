package com.example.mywallet

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.mywallet.base.BaseActivity
import com.example.mywallet.databinding.ActivityMainBinding
import com.example.mywallet.util.PrefsUtil
import com.example.mywallet.util.getObject
import com.example.mywallet.util.setObject

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutId: Int = R.layout.activity_main
    override val toolbarLayoutId: Int = R.layout.toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}