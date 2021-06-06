package com.example.mywallet.view.favorite

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.mywallet.R
import com.example.mywallet.base.BaseFragment
import com.example.mywallet.databinding.FragmentListCurrenciesBinding
import com.example.mywallet.model.Currency
import com.example.mywallet.model.Result
import com.example.mywallet.model.ResultStatus
import com.example.mywallet.view.listing.CurrenciesAdapter
import com.example.mywallet.view.listing.ListType
import com.example.mywallet.viewmodel.CurrencyViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ListFavoriteCurrenciesFragment : BaseFragment<FragmentListCurrenciesBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_list_currencies
    private val currencyViewModel: CurrencyViewModel by sharedViewModel(from = { requireActivity() })
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
            when (it.status) {
                ResultStatus.SUCCESS -> {
                    handleSuccessResponse(it)
                    currenciesAdapter.submitListCurrencies(it.data?: listOf())

                }
                ResultStatus.ERROR -> {
                    viewBinding.result.root.apply{
                       findViewById<ProgressBar>(R.id.pbLoading).visibility = View.GONE
                       findViewById<AppCompatImageView>(R.id.ivEmpty).visibility = View.VISIBLE
                       findViewById<AppCompatTextView>(R.id.tvEmpty).apply {
                            visibility = View.VISIBLE
                            text = "Something wrong happened."
                        }
                    }
                }
                else -> {

                }

            }
        })
    }

    private fun handleSuccessResponse(it: Result<List<Currency>>) {
        if (it.data.isNullOrEmpty()) {
            viewBinding.result.root.apply {
                findViewById<ProgressBar>(R.id.pbLoading).visibility = View.GONE
                findViewById<AppCompatImageView>(R.id.ivEmpty).apply {
                    visibility = View.VISIBLE
                    setOnClickListener {
                        findNavController().navigate(R.id.searchFragment)
                    }
                }
                findViewById<AppCompatTextView>(R.id.tvEmpty).apply {
                    visibility = View.VISIBLE
                    text =
                        "Your favorite list is empty.\nPlease discover our currency to find out your favorite"
                    setOnClickListener {
                        findNavController().navigate(R.id.searchFragment)
                    }
                }

            }

        } else {
            viewBinding.result.root.apply {
                findViewById<ProgressBar>(R.id.pbLoading).visibility = View.GONE
                findViewById<AppCompatImageView>(R.id.ivEmpty).visibility = View.GONE
                findViewById<AppCompatTextView>(R.id.tvEmpty).visibility = View.GONE
            }
        }
    }

}