package com.example.mywallet.view.favorite

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.mywallet.R
import com.example.mywallet.base.BaseFragment
import com.example.mywallet.databinding.FragmentListCurrenciesBinding
import com.example.mywallet.model.ResultStatus
import com.example.mywallet.view.listing.CurrenciesAdapter
import com.example.mywallet.view.listing.ListType
import com.example.mywallet.viewmodel.CurrencyViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ListFavoriteCurrenciesFragment : BaseFragment<FragmentListCurrenciesBinding>(){
    override val layoutId: Int
        get() = R.layout.fragment_list_currencies
    private val currencyViewModel : CurrencyViewModel by sharedViewModel(from = {requireActivity()} )
    private val currenciesAdapter: CurrenciesAdapter by lazy {
        CurrenciesAdapter(ListType.FAVORITE_LIST) { currency ->
            currencyViewModel.changeStatusFavorite(currency)
        }
    }

    companion object {
        fun newInstance(): ListFavoriteCurrenciesFragment = ListFavoriteCurrenciesFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currenciesAdapter.setHasStableIds(true)
    }

    override fun bindView() {
        super.bindView()
        viewBinding.rvCurrencies.apply {
            adapter = currenciesAdapter
            setHasFixedSize(true)
        }
        viewBinding.swRefresh.isEnabled = false
    }

    override fun onResume() {
        super.onResume()
        currencyViewModel.getListFavCurrencies()
    }

    override fun bindViewModel() {
        super.bindViewModel()
        currencyViewModel.listFavCurrencies.observe(this, Observer {
            when (it.status){
                ResultStatus.SUCCESS ->{
                    currenciesAdapter.submitListCurrencies(it.data?: listOf())
                    viewBinding.pbLoading.visibility = View.GONE
                }
                ResultStatus.ERROR ->{
                    viewBinding.pbLoading.visibility = View.GONE
                }
                else ->{

                }

            }
        })
    }

}