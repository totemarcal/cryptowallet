package com.example.cryptowallet.ViewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.cryptowallet.data.WalletRepository
import com.example.cryptowallet.data.Wallet
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import androidx.databinding.Observable
import com.example.cryptowallet.Api.DataWallet
import com.example.cryptowallet.Api.WalletApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

/**
 * Created by muhrahmatullah on 26/09/18.
 */
class WalletViewModel (private val walletRepository: WalletRepository): ViewModel(), Observable {

    private val erroApi = MutableLiveData<Boolean>()
    private val erroApiLoad = MutableLiveData<Boolean>()

    val client by lazy { WalletApiClient.create() }

    private val wallets = MutableLiveData<List<Wallet>>()

    fun getWallets(): LiveData<List<Wallet>>{
        return wallets
    }

    fun getWalletsOffline(): LiveData<List<Wallet>>{
        return walletRepository.getAll()
    }

    fun loadWallet(){
        try{
        client.getWallet()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    var list: ArrayList<Wallet> = ArrayList<Wallet>()
                    for (item in result){
                        list.add(Wallet(item.coin, item.quotation, item.variation, item.qtd, item.value, item.id))
                    }
                    wallets.postValue(list)
                },
                { throwable ->
                    Log.v("ErroApi","Add error: ${throwable.message}")
                    erroApiLoad.postValue(true)
                }
            )}catch (ex: Exception){
                Log.v("ErroApi","Add error: ${ex.message}")
            }
    }

    fun getErroApi(): MutableLiveData<Boolean>{
        return  erroApi
    }

    fun getErroApiLoad(): MutableLiveData<Boolean>{
        return  erroApiLoad
    }

    fun getWalletById(id: String): LiveData<Wallet> {
        return walletRepository.getWalletById(id)
    }

    fun insertWallet(wallet: Wallet){
        try{
        client.addWallet(DataWallet("", wallet.item_name, wallet.item_variation, wallet.item_quotation, wallet.item_qtd_wallet, wallet.item_value_wallet))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                            wallet.id=result.id
                            viewModelScope.launch(Dispatchers.IO) {
                                walletRepository.insertWallet(wallet)
                                loadWallet()
                            }
                       }, { throwable ->
                            Log.v("ErroApi","Add error: ${throwable.message}")
                            erroApi.postValue(true)
                        }
            )}catch (ex: Exception){
                Log.v("ErroApi","Add error: ${ex.message}")
            }
    }

    fun updateWallet(wallet: Wallet){
        client.updateWallet(wallet.id, DataWallet(wallet.id, wallet.item_name, wallet.item_variation, wallet.item_quotation, wallet.item_qtd_wallet, wallet.item_value_wallet))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewModelScope.launch(Dispatchers.IO) {
                    walletRepository.updateWallet(wallet)
                    loadWallet()
                }
            }, { throwable ->
                Log.v("ErroApi","Add error: ${throwable.message}")
                erroApi.postValue(true)
            })

    }

    fun deleteWallet(walletId: String){
        client.deleteWallet(walletId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                            viewModelScope.launch(Dispatchers.IO) {
                                walletRepository.deleteWallet(walletId)
                                loadWallet()
                            }
                       }, { throwable ->
                            Log.v("ErroApi","Add error: ${throwable.message}")
                            erroApi.postValue(true)
                       })
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }
}