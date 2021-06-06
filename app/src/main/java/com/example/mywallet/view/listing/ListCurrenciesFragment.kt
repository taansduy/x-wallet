package com.example.mywallet.view.listing

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.mywallet.R
import com.example.mywallet.base.BaseFragment
import com.example.mywallet.databinding.FragmentListCurrenciesBinding
import com.example.mywallet.model.ResultStatus
import com.example.mywallet.viewmodel.CurrencyViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ListCurrenciesFragment : BaseFragment<FragmentListCurrenciesBinding>(){
    override val layoutId: Int
        get() = R.layout.fragment_list_currencies
    private val currencyViewModel : CurrencyViewModel by sharedViewModel(from = {requireActivity()} )
    private val currenciesAdapter: CurrenciesAdapter by lazy {
        CurrenciesAdapter(ListType.NORMAL_LIST) { currency ->
            currencyViewModel.changeStatusFavorite(currency)
        }
    }

    companion object {
        fun newInstance(): ListCurrenciesFragment = ListCurrenciesFragment()
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
        viewBinding.swRefresh.setOnRefreshListener {
            currencyViewModel.getListCurrencies()
        }
        currencyViewModel.getListCurrencies()
    }

    override fun bindViewModel() {
        super.bindViewModel()
        currencyViewModel.listCurrencies.observe(this, Observer {
            when (it.status){
                ResultStatus.SUCCESS ->{
                    currenciesAdapter.submitListCurrencies(it.data?: listOf())
                    viewBinding.pbLoading.visibility = View.GONE
                    viewBinding.swRefresh.isRefreshing = false
                }
                ResultStatus.ERROR ->{
                    viewBinding.pbLoading.visibility = View.GONE
                    viewBinding.swRefresh.isRefreshing = false
                }
                else ->{

                }
            }
        })
    }

}