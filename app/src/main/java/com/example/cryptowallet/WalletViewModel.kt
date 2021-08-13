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

    private var mTriggerFetchData = MutableLiveData<Boolean>()
    private var wallets : LiveData<List<Wallet>> = walletRepository.allWallets

    fun getWallets(): LiveData<List<Wallet>>{
        return wallets
    }

    fun insertWallet(wallet: Wallet){
        viewModelScope.launch(Dispatchers.IO) {
            walletRepository.insertWallet(wallet)
        }
    }

    fun loadWallet() {
        mTriggerFetchData.value = true
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }
}