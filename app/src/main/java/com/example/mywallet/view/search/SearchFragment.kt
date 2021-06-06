package com.example.mywallet.view.search

import android.os.Bundle
import androidx.appcompat.widget.SearchView
import com.example.mywallet.R
import com.example.mywallet.base.BaseActivity
import com.example.mywallet.base.BaseFragment
import com.example.mywallet.databinding.FragmentSearchBinding
import com.example.mywallet.view.listing.CurrenciesAdapter
import com.example.mywallet.view.listing.ListType
import com.example.mywallet.viewmodel.CurrencyViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_search
    private val currencyViewModel: CurrencyViewModel by sharedViewModel(from = { requireActivity() })
    private val currenciesAdapter: CurrenciesAdapter by lazy {
        CurrenciesAdapter(ListType.NORMAL_LIST) { currency ->
            currencyViewModel.changeStatusFavorite(currency)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currenciesAdapter.setHasStableIds(true)
    }

    override fun bindView() {
        super.bindView()
        (activity as? BaseActivity<*>)?.removeToolbar()
        viewBinding.svCurrency.onActionViewExpanded()
        viewBinding.rvCurrencies.apply {
            adapter = currenciesAdapter
            setHasFixedSize(true)
        }
        viewBinding.svCurrency.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = handleQueryText(query)


            override fun onQueryTextChange(newText: String?): Boolean = handleQueryText(newText)


        })
    }

    private fun handleQueryText(query: String?): Boolean {
        if (query.isNullOrEmpty()) {
            currenciesAdapter.submitListCurrencies(listOf())
            return true
        }
        var currentList = currencyViewModel.listCurrencies.value?.data
        query.let {
            currentList = currentList?.filter { currency ->
                currency.base.toLowerCase(Locale.getDefault()).contains(
                    it.toLowerCase(
                        Locale.getDefault()
                    )
                ) || currency.counter.toLowerCase(Locale.getDefault()).contains(
                    it.toLowerCase(
                        Locale.getDefault()
                    )
                )
            }
        }
        currenciesAdapter.submitListCurrencies(currentList ?: listOf())
        return true
    }


}