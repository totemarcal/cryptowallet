package com.example.cryptowallet.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Wallet::class], version = 1, exportSchema = false)
abstract class WalletDatabase: RoomDatabase() {
    abstract fun getWalletDao(): WalletDao

    companion object{
        @Volatile
        private var INSTANCE: WalletDatabase? = null
        fun getInstance(context: Context): WalletDatabase{
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WalletDatabase::class.java,
                        "wallet.db"
                    ).build()
                }
                return instance
            }
        }
    }
}