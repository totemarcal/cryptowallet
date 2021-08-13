package com.example.cryptowallet

import android.content.DialogInterface
import android.content.Intent
import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.cryptowallet.data.Wallet
import com.example.cryptowallet.data.WalletDatabase
import com.example.cryptowallet.data.WalletRepository
import com.example.cryptowallet.databinding.ActivityDetailBinding
import kotlinx.android.synthetic.main.input_dialog.view.*

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: WalletViewModel
    private lateinit var wallet : Wallet
    private lateinit var walletId:String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val walletDao = WalletDatabase.getInstance(application).getWalletDao()
        val walletRepository = WalletRepository(walletDao)
        val walletViewModelFactory = WalletViewModelFactory(walletRepository)
        viewModel = ViewModelProvider(this, walletViewModelFactory).get(WalletViewModel::class.java)
        walletId = intent.getLongExtra("wallet_id", 0).toString()
        viewModel.getWalletById(walletId).observe(this, Observer {
            binding.txId.text = it.id.toString()
            binding.txItemName.text = it.item_name
            binding.txItemQtdWallet.text = it.item_qtd_wallet
            binding.txItemQuotation.text = it.item_quotation
            binding.txItemValueWallet.text = it.item_value_wallet
            binding.txItemVariation.text = it.item_variation
        })

        binding.btCancel.setOnClickListener { v ->
            val intent = Intent(v.context, MainActivity::class.java)
            v.context.startActivity(intent)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

}
