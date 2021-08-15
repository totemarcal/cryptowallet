package com.example.cryptowallet.Api

import com.google.gson.annotations.SerializedName

data class DataWallet( val id: String,
                       val coin: String,
                       val variation: String,
                       val quotation: String,
                       val qtd:String,
                       val value: String)
