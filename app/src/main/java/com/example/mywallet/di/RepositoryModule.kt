package com.example.mywallet.di

import com.example.mywallet.repository.wallet.RemoteWalletRepository
import com.example.mywallet.repository.wallet.WalletRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
    single<WalletRepository> { RemoteWalletRepository(get(), get()) }
}