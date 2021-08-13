package com.example.cryptowallet.data

import androidx.lifecycle.LiveData

class WalletRepository(private val walletDao: WalletDao) {


    suspend fun insertWallet(wallet: Wallet){
            walletDao.insert(wallet)
    }

    fun getWalletById(id: String) : LiveData<Wallet>{
        return walletDao.getById(id)
    }

    fun getAll() : LiveData<List<Wallet>>{
        return walletDao.getAll()
    }
}