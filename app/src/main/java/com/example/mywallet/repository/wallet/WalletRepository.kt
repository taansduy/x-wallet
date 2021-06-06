package com.example.mywallet.repository.wallet

import com.example.mywallet.model.Currency
import com.example.mywallet.model.ListCurrencyResponse

interface WalletRepository{
    suspend fun getListCurrencies() : List<Currency>
    suspend fun getListFavCurrencies() : List<Currency>
    suspend fun addFavoriteCurrency(currency: Currency)
    suspend fun removeFavoriteCurrency(currency: Currency)
}