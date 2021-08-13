package com.example.cryptowallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cryptowallet.data.WalletRepository
import java.lang.IllegalArgumentException

class WalletViewModelFactory(private val localRepository: WalletRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WalletViewModel::class.java)){
            return WalletViewModel(localRepository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}
