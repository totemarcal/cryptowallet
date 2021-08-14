package com.example.cryptowallet.Api

import com.google.gson.annotations.SerializedName

data class DataWallet( val id: String,
                       val coin: String,
                       val variation: String,
                       val quotation: String,
                       val qtd:String,
                       val value: String)

data class WalletList (
    @SerializedName("wallets" )
    val wallets: List<DataWallet>
)
data class WalletEmbedded (
    @SerializedName("_embedded" )
    val list: WalletList
)