package com.example.cryptowallet

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptowallet.data.Wallet
import com.example.cryptowallet.databinding.CryptoItemViewBinding

class WalletRecyclerViewAdapter (private val walletList : List<Wallet>)
    : RecyclerView.Adapter<WalletRecyclerViewAdapter.WalletViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CryptoItemViewBinding.inflate(inflater)
        return WalletViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WalletViewHolder, position: Int) {
        holder.bindView(walletList[position])
        holder.cardView.setOnClickListener{ v ->
            val intent = Intent(v.context, DetailsActivity::class.java)
            intent.putExtra("wallet_id", walletList[position].id)
            v.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return walletList.size
    }

    class WalletViewHolder(private val binding: CryptoItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val cardView = binding.itemContainer
        fun bindView(wallet: Wallet) {
            binding.wallet = wallet
        }
    }
}

