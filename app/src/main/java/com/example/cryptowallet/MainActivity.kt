package com.example.cryptowallet

import androidx.lifecycle.Observer
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptowallet.data.Wallet
import com.example.cryptowallet.data.WalletDatabase
import com.example.cryptowallet.data.WalletRepository
import com.example.cryptowallet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: WalletRecyclerViewAdapter
    private lateinit var viewModel: WalletViewModel
    private val wallets : ArrayList<Wallet> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
            binding.itemRecyclerview.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            adapter = WalletRecyclerViewAdapter(wallets)
            binding.itemRecyclerview.adapter = adapter
            val studentDao = WalletDatabase.getInstance(application).getWalletDao()
            val walletRepository = WalletRepository(studentDao)
            val mainActivityViewModelFactory = WalletViewModelFactory(walletRepository)
            /**
             * get student view model class instance
             */
            viewModel = ViewModelProvider(this, mainActivityViewModelFactory).get(WalletViewModel::class.java)

            //setData()
        }catch (e: Exception) {
            println("Caught ArithmeticException")
        }
        viewModel.getWallets().observe(this, Observer<List<Wallet>> {
            wallets.clear()
            wallets.addAll(it!!)
            adapter.notifyDataSetChanged()
        })

        viewModel.loadWallet()

        /*binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/
    }

    private fun setData(){
        val item_name = "BTC"
        val item_quatation = "BTC"
        val item_variation = "BTC"
        val item_qtd_wallet = "BTC"
        val item_value_wallet = "BTC"
        viewModel.insertWallet(Wallet(item_name,
            item_quatation, item_variation, item_qtd_wallet, item_value_wallet))
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
