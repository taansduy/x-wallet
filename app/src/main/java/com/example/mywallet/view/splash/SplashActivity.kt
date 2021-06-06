package com.example.mywallet.view.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.mywallet.MainActivity
import com.example.mywallet.R
import com.example.mywallet.util.PrefsUtil
import com.example.mywallet.util.getObject
import kotlinx.coroutines.delay
import org.jetbrains.anko.startActivity


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        val currentTheme = PrefsUtil.ref.getObject<Int>(PrefsUtil.APP_THEME)
        if(currentTheme==null || currentTheme==0)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        Handler(Looper.getMainLooper()).postDelayed({
            finish()
            startActivity<MainActivity>()
        }, 1000)
    }
}