package com.example.cryptowallet.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "wallet")
data class Wallet(@ColumnInfo(name = "item_name")var item_name: String,
                   @ColumnInfo(name = "item_quatation")var item_quotation: String,
                   @ColumnInfo(name = "item_variation")var item_variation: String,
                   @ColumnInfo(name = "item_qtd_wallet")var item_qtd_wallet: String,
                   @ColumnInfo(name = "item_value_wallet")var item_value_wallet: String,
                   @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Long = 0): Parcelable



