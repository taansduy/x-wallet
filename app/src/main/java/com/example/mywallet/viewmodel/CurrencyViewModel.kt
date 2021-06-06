package com.example.mywallet.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywallet.model.Currency
import com.example.mywallet.model.Result
import com.example.mywallet.repository.wallet.WalletRepository
import com.example.mywallet.util.perform
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrencyViewModel(private val remoteRepository: WalletRepository) :ViewModel(){
    val listCurrencies = MutableLiveData<Result<List<Currency>>>(Result.loading())
    val listFavCurrencies = MutableLiveData<Result<List<Currency>>>(Result.loading())

    fun getListCurrencies() = viewModelScope.perform(listCurrencies) {
        remoteRepository.getListCurrencies()
    }

    fun getListFavCurrencies() = viewModelScope.perform(listFavCurrencies) {
        remoteRepository.getListFavCurrencies()
    }

    fun changeStatusFavorite(currency:Currency) = viewModelScope.launch(Dispatchers.IO){
        if(currency.is_favorite){
            remoteRepository.addFavoriteCurrency(currency)
        }
        else{
            remoteRepository.removeFavoriteCurrency(currency)
        }
        val currentList = listCurrencies.value?.data
        currentList?.let {
            currentList.find {item -> item.base == currency.base && item.counter==currency.counter }?.is_favorite =currency.is_favorite
            listCurrencies.postValue(Result.success(data = currentList))
            listFavCurrencies.postValue(Result.success(data = currentList.filter { it.is_favorite }))
        }
    }

}