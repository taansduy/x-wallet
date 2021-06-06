package com.example.mywallet.view.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mywallet.view.favorite.ListFavoriteCurrenciesFragment
import com.example.mywallet.view.listing.ListCurrenciesFragment

class HomePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    companion object {
        const val NUM_PAGE = 2
    }

    override fun getItemCount(): Int = NUM_PAGE

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> ListFavoriteCurrenciesFragment.newInstance()
            else -> ListCurrenciesFragment.newInstance()
        }
}