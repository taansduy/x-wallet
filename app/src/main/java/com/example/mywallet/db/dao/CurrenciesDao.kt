package com.example.mywallet.db.dao

import androidx.room.*
import com.example.mywallet.model.Currency

@Dao
interface CurrenciesDao {

    @Query("SELECT * FROM Currencies")
    fun getAll(): List<Currency>

    @Query("SELECT * FROM Currencies WHERE is_favorite =:isFavorite")
    fun getFavorite(isFavorite: Boolean = true): List<Currency>

    @Query("UPDATE Currencies SET is_favorite=:isFavorite WHERE base = :base AND counter=:counter")
    fun updateFavoriteCurrency(isFavorite: Boolean, base: String, counter: String)

    @Delete
    fun removeCurrency(currency: Currency)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(currencies: List<Currency>)
}