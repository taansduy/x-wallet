package com.example.mywallet.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mywallet.db.dao.CurrenciesDao
import com.example.mywallet.model.Currency

@Database(
    entities = [Currency::class],
    version = 1, exportSchema = false
)

abstract class MyWalletDB : RoomDatabase() {
    abstract val currenciesDao: CurrenciesDao
}