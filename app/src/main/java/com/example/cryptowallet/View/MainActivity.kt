package com.example.cryptowallet.View

import android.content.DialogInterface
import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptowallet.Adapter.WalletRecyclerViewAdapter
import com.example.cryptowallet.R
import com.example.cryptowallet.ViewModel.WalletViewModel
import com.example.cryptowallet.ViewModel.WalletViewModelFactory
import com.example.cryptowallet.data.Wallet
import com.example.cryptowallet.data.WalletDatabase
import com.example.cryptowallet.data.WalletRepository
import com.example.cryptowallet.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.input_dialog.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: WalletRecyclerViewAdapter
    private lateinit var viewModel: WalletViewModel
    private val wallets : ArrayList<Wallet> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.itemRecyclerview.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = WalletRecyclerViewAdapter(wallets)
        binding.itemRecyclerview.adapter = adapter
        val walletDao = WalletDatabase.getInstance(application).getWalletDao()
        val walletRepository = WalletRepository(walletDao)
        val walletViewModelFactory = WalletViewModelFactory(walletRepository)
        viewModel = ViewModelProvider(this, walletViewModelFactory).get(WalletViewModel::class.java)

        viewModel.getErroApi().observe(this, Observer {
            val builder = AlertDialog.Builder(this)
            builder.setMessage(R.string.dialog_error_api)
                .setPositiveButton(
                    R.string.fire,
                    DialogInterface.OnClickListener { dialog, id ->
                    })
            // Create the AlertDialog object and return it
            builder.create().show()
        })
        viewModel.getWallets().observe(this, Observer<List<Wallet>> {
            wallets.clear()
            wallets.addAll(it!!)
            adapter.notifyDataSetChanged()
        })

        binding.fab.setOnClickListener { view ->
            val dialogBuilder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.input_dialog, null)
            dialogBuilder.setView(view)
            dialogBuilder.setTitle("Buy Coins")
            val et_cryptocurrency = view.ed_cryptocurrency
            val et_quantum = view.ed_quantum
            dialogBuilder.setPositiveButton("Buy") { _: DialogInterface, _: Int ->
                val item_name = et_cryptocurrency.text
                val item_qtd_wallet = et_quantum.text
                //val item_value_wallet : String = item_qtd_wallet * 34756.56
                val item_value_wallet : String = "$45.675.56"
                viewModel.insertWallet(
                    Wallet(item_name.toString(), "$34.756,56", "+1.0968", item_qtd_wallet.toString(), item_value_wallet)
                )
                Toast.makeText(this, "Currency added to your wallet.", Toast.LENGTH_SHORT).show()

            }
            dialogBuilder.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->
            }
            dialogBuilder.show()
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
