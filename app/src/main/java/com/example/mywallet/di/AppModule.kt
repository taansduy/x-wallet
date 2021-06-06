package com.example.mywallet.di

import com.example.mywallet.viewmodel.CurrencyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    viewModel { CurrencyViewModel(get()) }
}