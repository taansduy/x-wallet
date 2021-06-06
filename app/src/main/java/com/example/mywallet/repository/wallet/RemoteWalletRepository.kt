package com.example.mywallet.repository.wallet

import com.example.mywallet.api.ApiService
import com.example.mywallet.db.dao.CurrenciesDao
import com.example.mywallet.model.Currency

class RemoteWalletRepository(
    private val api: ApiService,
    private val currenciesDao: CurrenciesDao
) : WalletRepository {
    override suspend fun getListCurrencies(): List<Currency> {
        var cachedData: List<Currency>? = null
        try {
            cachedData = currenciesDao.getAll()
        } catch (e: Exception) {

        }
        var response = listOf<Currency>()
        try {
            val remoteData = api.getListCurrencies().data
            if (!remoteData.isNullOrEmpty()) {
                cachedData?.let {
                    for (currency in cachedData) {
                        val index =
                            remoteData.indexOfFirst { it.base == currency.base && it.counter == currency.counter }
                        if (index == -1) {
                            currenciesDao.removeCurrency(currency)
                        } else {
                            remoteData[index].is_favorite = currency.is_favorite
                        }
                    }
                    currenciesDao.addAll(remoteData)
                }
                response = remoteData
            } else {
                cachedData?.let {
                    response = cachedData
                }
            }
            return response
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getListFavCurrencies(): List<Currency> = currenciesDao.getFavorite()

    override suspend fun addFavoriteCurrency(currency: Currency) =
        currenciesDao.updateFavoriteCurrency(true, currency.base, currency.counter)

    override suspend fun removeFavoriteCurrency(currency: Currency) =
        currenciesDao.updateFavoriteCurrency(false,currency.base?:"",currency.counter?:"")

}