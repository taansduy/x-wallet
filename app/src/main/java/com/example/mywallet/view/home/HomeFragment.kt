package com.example.mywallet.view.home

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import com.example.mywallet.MainApplication
import com.example.mywallet.R
import com.example.mywallet.base.BaseFragment
import com.example.mywallet.databinding.FragmentHomeBinding
import com.example.mywallet.util.PrefsUtil
import com.example.mywallet.util.getObject
import com.example.mywallet.util.setObject
import com.example.mywallet.viewmodel.CurrencyViewModel
import com.google.android.material.tabs.TabLayoutMediator
import org.jetbrains.anko.find
import org.koin.android.ext.android.inject

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_home
    override val toolbarLayout: Int
        get() = R.layout.toolbar

    override fun bindView() {
        super.bindView()
        viewBinding.vpHome.adapter = HomePagerAdapter(this)
        viewBinding.vpHome.offscreenPageLimit = 2
        TabLayoutMediator(viewBinding.tlHome, viewBinding.vpHome) { tab, position ->
            tab.text = when (position) {
                0 -> "Favorite List"
                else -> "USD"
            }
        }.attach()
    }

    override fun toolbarFunc(curActivity: AppCompatActivity?, toolbar: Toolbar?) {
        super.toolbarFunc(curActivity, toolbar)
        toolbar?.find<AppCompatImageView>(R.id.ivSearch)?.setOnClickListener {
            navController.navigate(R.id.searchFragment)
        }
        toolbar?.find<AppCompatImageView>(R.id.ivChangeTheme)?.setOnClickListener {
            val currentTheme = PrefsUtil.ref.getObject<Int>(PrefsUtil.APP_THEME)
            if(currentTheme==null || currentTheme==0)
            {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                PrefsUtil.ref.setObject(PrefsUtil.APP_THEME,1)
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                PrefsUtil.ref.setObject(PrefsUtil.APP_THEME,0)
            }
        }
    }

}