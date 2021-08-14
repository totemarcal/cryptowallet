package com.example.cryptowallet.View

import android.content.DialogInterface
import android.content.Intent
import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.cryptowallet.R
import com.example.cryptowallet.ViewModel.WalletViewModel
import com.example.cryptowallet.ViewModel.WalletViewModelFactory
import com.example.cryptowallet.data.Wallet
import com.example.cryptowallet.data.WalletDatabase
import com.example.cryptowallet.data.WalletRepository
import com.example.cryptowallet.databinding.ActivityDetailBinding

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
            wallet = it
            binding.txId.text = it.id.toString()
            binding.txItemName.text = "Coins: " + it.item_name
            binding.edAmount.setText(it.item_qtd_wallet)
            binding.txItemQuotation.text = "Quotation: "+it.item_quotation
            binding.txItemValueWallet.text = "Value: "+it.item_value_wallet
            binding.txItemVariation.text = "Variation: "+ it.item_variation
        })

        binding.btCancel.setOnClickListener { v ->
            val intent = Intent(v.context, MainActivity::class.java)
            v.context.startActivity(intent)
        }

        binding.btRansom.setOnClickListener { v ->
            val builder = AlertDialog.Builder(this)
            builder.setMessage(R.string.dialog_fire_missiles)
                .setPositiveButton(
                    R.string.fire,
                    DialogInterface.OnClickListener { dialog, id ->
                        viewModel.deleteWallet(binding.txId.text as String)
                    })
                .setNegativeButton(
                    R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog
                    })
            // Create the AlertDialog object and return it
            builder.create().show()
        }

        binding.btUpdate.setOnClickListener { v ->
            val builder = AlertDialog.Builder(this)
            builder.setMessage(R.string.dialog_fire_update)
                .setPositiveButton(
                    R.string.fire,
                    DialogInterface.OnClickListener { dialog, id ->
                        wallet.item_qtd_wallet = binding.edAmount.text.toString()
                        viewModel.updateWallet(wallet)
                    })
                .setNegativeButton(
                    R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog
                    })
            // Create the AlertDialog object and return it
            builder.create().show()
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
