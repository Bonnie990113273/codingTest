package com.jike.cxm.codingtest.model.repository

import com.jike.cxm.codingtest.model.local.db.AppDatabase
import com.jike.cxm.codingtest.model.local.entity.CurrencyEntity

class CurrencyRepository(private val appDatabase: AppDatabase) {
    suspend fun insert(currencyEntity: CurrencyEntity) =
        appDatabase.currencyDao().insert(currencyEntity)

    suspend fun update(currencyEntity: CurrencyEntity) =
        appDatabase.currencyDao().update(currencyEntity)

    suspend fun deleteAll() = appDatabase.currencyDao().deleteAllData()

    suspend fun delete(currencyEntity: CurrencyEntity) =
        appDatabase.currencyDao().delete(currencyEntity)

    suspend fun getMaxCurrencyId() = appDatabase.currencyDao().getMaxCurrencyId()
    suspend fun getAllCurrencyInfoFromDb() = appDatabase.currencyDao().getAllCurrencyFromDb()

    suspend fun getCurrencyListFromDb() = appDatabase.currencyDao().getCurrencyListFromDb()
}