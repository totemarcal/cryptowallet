package com.example.cryptowallet.data

import androidx.lifecycle.LiveData

class WalletRepository(private val walletDao: WalletDao) {


    suspend fun insertWallet(wallet: Wallet){
            walletDao.insert(wallet)
    }

    suspend fun deleteWallet(walletId: String){
        walletDao.deleteWallet(walletId)
    }

    fun getWalletById(id: String) : LiveData<Wallet>{
        return walletDao.getById(id)
    }

    fun getAll() : LiveData<List<Wallet>>{
        return walletDao.getAll()
    }
}