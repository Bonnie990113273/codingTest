package com.jike.cxm.codingtest.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.jike.cxm.codingtest.model.local.db.AppDatabase
import com.jike.cxm.codingtest.model.local.entity.CurrencyEntity
import com.jike.cxm.codingtest.model.repository.CurrencyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class CurrentInfoViewModel(application: Application) : AndroidViewModel(application) {

    //伴生对象
    companion object {
        const val TAG = "CurrentInfoViewModel"
    }

    val currencyListFromDb: MutableLiveData<List<CurrencyEntity>> = MutableLiveData()
    val isError: MutableLiveData<String> = MutableLiveData()
    val progress: MutableLiveData<Boolean> = MutableLiveData()
    private var index: Int = 0

    private val currencyRepository: CurrencyRepository
    private var context: Context? = null

    init {
        currencyRepository = CurrencyRepository(AppDatabase.getInstance(application))
        context = application
    }

    private fun deleteAll() {
        try {
            CoroutineScope(Dispatchers.Main).launch {
                currencyRepository.deleteAll()
            }
        } catch (error: Exception) {
            Log.e(TAG, error.message.toString())
        }
    }

     fun onLoadClick(){
        Log.e(TAG,"load")
//        deleteAll()
        insertData()
    }

     fun onSortClick(){
        getOrderedCurrencyList()
    }

    private fun insertData() {
        try {
            CoroutineScope(Dispatchers.Main).launch {
                //暂时用自动加载数据代替
                progress.postValue(true)
                val entity1 = CurrencyEntity(index, "BTC", "Bitcoin", "BTC")
                index++
                val entity2 = CurrencyEntity(index, "ETH", "Ethereum", "ETH")
                index++
                val entity3 = CurrencyEntity(index, "XRP", "XRP", "XRP")
                index++
                val entity4 = CurrencyEntity(index, "BCH", "Bitcoin Cash", "BCH")
                index++
                val entity5 = CurrencyEntity(index, "LTC", "LiteCoin", "LTC")
                index++
                val entity6 = CurrencyEntity(index, "EOS", "EOS", "EOS")
                index++
                val entity7 = CurrencyEntity(index, "BNB", "Binance Coin", "BNB")
                index++
                val entity8 = CurrencyEntity(index, "LINK", "ChainLink", "LINK")
                index++
                val entity9 = CurrencyEntity(index, "NEO", "NEO", "NEO")
                index++
                val entity10 = CurrencyEntity(index, "ETC", "Ethereum Classic", "ETC")
                index++
                val entity11 = CurrencyEntity(index, "ONT", "Ontology", "ONT")
                index++
                val entity12 = CurrencyEntity(index, "CRO", "Crypto.com Chain", "CRO")
                index++
                val entity13 = CurrencyEntity(index, "CUC", "Cucumber", "CUC")
                index++
                val entity14 = CurrencyEntity(index, "USDC", "USD Coin", "USDC")

                currencyRepository.insert(entity1)
                currencyRepository.insert(entity2)
                currencyRepository.insert(entity3)
                currencyRepository.insert(entity4)
                currencyRepository.insert(entity5)
                currencyRepository.insert(entity6)
                currencyRepository.insert(entity7)
                currencyRepository.insert(entity8)
                currencyRepository.insert(entity9)
                currencyRepository.insert(entity10)
                currencyRepository.insert(entity11)
                currencyRepository.insert(entity12)
                currencyRepository.insert(entity13)
                currencyRepository.insert(entity14)
                progress.postValue(false)
            }
            getCurrencyListFromDb()

        } catch (error: Exception) {
            Log.e(TAG, error.message.toString())
        }
    }

    private fun getCurrencyListFromDb() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                progress.postValue(true)
                val data = currencyRepository.getCurrencyListFromDb();
                currencyListFromDb.postValue(data)
                progress.postValue(false)
            }
        } catch (error: Exception) {
            Log.e(TAG, error.message.toString())
        }
    }

    private fun getOrderedCurrencyList(){
        try {
            CoroutineScope(Dispatchers.IO).launch {
                progress.postValue(true)
                val data = currencyRepository.getAllCurrencyInfoFromDb();
                currencyListFromDb.postValue(data)
                progress.postValue(false)
            }
        } catch (error: Exception) {
            Log.e(TAG, error.message.toString())
        }
    }
}
