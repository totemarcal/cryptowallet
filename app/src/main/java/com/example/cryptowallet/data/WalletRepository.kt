package com.example.cryptowallet.data


/**
 * Created by muhrahmatullah on 29/09/18.
 */
class WalletRepository(private val walletDao: WalletDao) {

    val allWallets = walletDao.getAll()

    suspend fun insertWallet(wallet: Wallet){
        try {
            walletDao.insert(wallet)
        }catch (e: Exception) {
            println("Caught ArithmeticException")
        }
    }
}