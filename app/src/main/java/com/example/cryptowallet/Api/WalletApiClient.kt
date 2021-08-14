package com.example.cryptowallet.Api

import com.example.cryptowallet.data.Wallet
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface WalletApiClient {

    @GET("wallet") fun getMovies(): Observable<WalletEmbedded>
    @POST("wallet") fun addWallet(@Body wallet: DataWallet): Completable
    @DELETE("wallet/{id}") fun deleteMovie(@Path("id") id: Int) : Completable
    @PUT("wallet/{id}") fun updateMovie(@Path("id")id: Int, @Body wallet: DataWallet) : Completable

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