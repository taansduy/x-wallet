package com.example.mywallet.di

import android.app.Application
import androidx.room.Room
import com.example.mywallet.db.MyWalletDB
import com.example.mywallet.db.dao.CurrenciesDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

const val PREFS_FILENAME = "com.example.myWallet"

val localModule = module {
    single { provideDatabase(androidApplication()) }
    single { provideCurrenciesDao(get()) }
}

fun provideDatabase(application: Application): MyWalletDB {
    return Room.databaseBuilder(application, MyWalletDB::class.java, "Currencies")
        .fallbackToDestructiveMigration()
        .build()
}

fun provideCurrenciesDao(database: MyWalletDB): CurrenciesDao {
    return  database.currenciesDao
}
