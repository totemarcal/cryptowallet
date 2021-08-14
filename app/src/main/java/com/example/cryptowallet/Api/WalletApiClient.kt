package com.example.cryptowallet.Api

import com.example.cryptowallet.data.Wallet
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface WalletApiClient {


    @POST("wallet") fun addWallet(@Body wallet: DataWallet): Completable

    companion object {

        fun create(): WalletApiClient {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://5fa103ace21bab0016dfd97e.mockapi.io/api/v1/")
                .build()

            return retrofit.create(WalletApiClient::class.java)
        }
    }
}