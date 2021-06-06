package com.example.mywallet.view.listing

import android.content.Context
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mywallet.R
import com.example.mywallet.databinding.ItemCurrencyBinding
import com.example.mywallet.databinding.ItemFavoriteCurrencyBinding
import com.example.mywallet.model.Currency


class CurrenciesAdapter(
    private val type: ListType,
    private val changeFavoriteStatusCallback: (Currency) -> Unit = { _ -> }

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var currencies = listOf<Currency>()
    private var mContext: Context? = null

    fun submitListCurrencies(newCurrencies: List<Currency>) {
        currencies = newCurrencies
        notifyDataSetChanged()
    }

    fun clearData() {
        currencies = listOf()
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun getItem(position: Int): Currency = currencies[position]


    override fun getItemCount(): Int = currencies.size

    override fun getItemViewType(position: Int): Int =
        when (type) {
            ListType.NORMAL_LIST -> R.layout.item_currency
            else -> R.layout.item_favorite_currency
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mContext = parent.context
        return when (viewType) {
            R.layout.item_currency -> {
                val binding =
                    ItemCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ItemCurrencyViewHolder(binding)
            }
            else -> {
                val binding =
                    ItemFavoriteCurrencyBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                ItemFavoriteCurrencyViewHolder(binding)
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemCurrencyViewHolder -> {
                holder.bind(
                    currencies[position],
                    position
                )
            }
            is ItemFavoriteCurrencyViewHolder -> {
                holder.bind(currencies[position], position)
            }
        }
    }


    inner class ItemCurrencyViewHolder(private val binding: ItemCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currency: Currency, position: Int) {
            binding.currency = currency
            binding.ivIsFavorite.setOnClickListener {
                currency.is_favorite = !currency.is_favorite
                changeFavoriteStatusCallback.invoke(currency)
                notifyItemChanged(position)
            }
            val source = "${currency.base} /${currency.counter}"
            val res = SpannableString(source)
            res.setSpan(RelativeSizeSpan(1.5f), 0, source.indexOfFirst { it == '/' }, 0) // set size
            mContext?.let {context ->
                res.setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(context, R.color.primaryText)),
                    0,
                    source.indexOfFirst { it == '/' },
                    0
                )

            }
            binding.tvBaseCounter.text = res
        }
    }

    inner class ItemFavoriteCurrencyViewHolder(private val binding: ItemFavoriteCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currency: Currency, position: Int) {
            binding.currency = currency
            binding.ivIsFavorite.setOnClickListener {
                currency.is_favorite = !currency.is_favorite
                changeFavoriteStatusCallback.invoke(currency)
                notifyDataSetChanged()
            }
            val source = "${currency.base} /${currency.counter}"
            val res = SpannableString(source)
            res.setSpan(RelativeSizeSpan(1.5f), 0, source.indexOfFirst { it == '/' }, 0) // set size
            mContext?.let {context ->
                res.setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(context, R.color.primaryText)),
                    0,
                    source.indexOfFirst { it == '/' },
                    0
                )

            }
            binding.tvBaseCounter.text = res
        }
    }

}

enum class ListType {
    NORMAL_LIST,
    FAVORITE_LIST
}