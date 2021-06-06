package com.example.mywallet.api

import com.example.mywallet.model.Currency
import com.example.mywallet.model.ListCurrencyResponse
import retrofit2.http.GET

interface ApiService {
    @GET("price/all_prices_for_mobile?counter_currency=USD")
    suspend fun getListCurrencies(): ListCurrencyResponse
}