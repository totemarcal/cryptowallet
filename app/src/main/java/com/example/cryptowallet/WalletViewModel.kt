package com.example.cryptowallet

import androidx.lifecycle.ViewModel
import com.example.cryptowallet.data.WalletRepository
import com.example.cryptowallet.data.Wallet
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import androidx.databinding.Observable
import kotlinx.coroutines.Dispatchers

/**
 * Created by muhrahmatullah on 26/09/18.
 */
class WalletViewModel (private val walletRepository: WalletRepository): ViewModel(), Observable {

    fun getWallets(): LiveData<List<Wallet>>{
        return walletRepository.getAll()
    }

    fun getWalletById(id: String): LiveData<Wallet> {
        return walletRepository.getWalletById(id)
    }

    fun insertWallet(wallet: Wallet){
        viewModelScope.launch(Dispatchers.IO) {
            walletRepository.insertWallet(wallet)
        }
    }

    fun updateWallet(wallet: Wallet){
        viewModelScope.launch(Dispatchers.IO) {
            walletRepository.updateWallet(wallet)
        }
    }

    fun deleteWallet(walletId: String){
        viewModelScope.launch(Dispatchers.IO) {
            walletRepository.deleteWallet(walletId)
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }
}