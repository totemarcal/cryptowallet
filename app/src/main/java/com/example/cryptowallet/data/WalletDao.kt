package com.example.cryptowallet.data

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import io.reactivex.Flowable
import com.example.cryptowallet.data.Wallet
/**
 * Created by muhrahmatullah on 12/08/18.
 */
@Dao
interface WalletDao {
    @Query("SELECT * from wallet")
    fun getAll(): LiveData<List<Wallet>>

    @Insert(onConflict = REPLACE)
    fun insert(wallet: Wallet)

    @Query("SELECT * FROM wallet WHERE id = :id ")
    fun getById(id: String): LiveData<Wallet>

}