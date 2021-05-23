package com.jike.cxm.codingtest.model.local.dao

import androidx.room.*
import com.jike.cxm.codingtest.model.local.entity.CurrencyEntity


@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currencyInfoEntity: CurrencyEntity): Long

    @Update
    suspend fun update(currencyInfoEntity: CurrencyEntity): Int

    @Delete
    suspend fun delete(currencyInfoEntity: CurrencyEntity): Int

    @Query("DELETE FROM currency_entity")
    suspend fun deleteAllData():Int

    @Query("DELETE FROM currency_entity WHERE name = :name")
    suspend fun deleteCurrencyFromDb(name : String): Int

    @Query("SELECT * FROM currency_entity ORDER BY id asc")
    suspend fun getAllCurrencyFromDb(): List<CurrencyEntity>

    @Query("SELECT * FROM currency_entity")
    suspend fun getCurrencyListFromDb():List<CurrencyEntity>

    @Query("SELECT MAX(id) FROM currency_entity")
    suspend fun getMaxCurrencyId(): Int?

}