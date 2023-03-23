package com.example.cft.ui.screen.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cft.databinding.ActivityMainBinding
import com.example.cft.ui.screen.info.view.BinInfoActivity
import com.example.cft.ui.screen.main.adapters.BinHistoryAdapter
import com.example.cft.ui.screen.main.adapters.BinItemActionListener
import com.example.cft.ui.screen.main.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val PROGRESSBAR_DELAY: Long = 500
private const val BIN_NOT_FOUND_TOAST_TEXT = "BIN not found"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: BinHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.findBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                showBinInfo(binding.textInput.text.toString())
            }
        }
        binding.clearBtn.setOnClickListener {
            viewModel.clearBinHistory()
            adapter.data = listOf()
        }

        val linearLayoutManager = LinearLayoutManager(this)
        binding.binHistoryList.layoutManager = linearLayoutManager

        adapter = BinHistoryAdapter(object : BinItemActionListener {
            override suspend fun onItemClicked(bin: String) {
                CoroutineScope(Dispatchers.IO).launch {
                    showBinInfo(bin)
                }
            }
        })
        adapter.data = viewModel.loadBinHistory()
        binding.binHistoryList.adapter = adapter
    }

    private suspend fun showBinInfo(bin: String) {
        CoroutineScope(Dispatchers.Main).launch {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            binding.progressBar.visibility = View.VISIBLE
        }

        val data = viewModel.findBinInfo(bin)
        delay(PROGRESSBAR_DELAY)

        CoroutineScope(Dispatchers.Main).launch {
            if (data != null) {
                val intent = Intent(this@MainActivity, BinInfoActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                intent.putExtra("BinData", Json.encodeToString(data))
                intent.putExtra("BinNumber", bin)
                startActivity(intent)

                adapter.data = listOf(bin) + (adapter.data - bin)
                viewModel.updateBinHistory(adapter.data)
            } else {
                Toast.makeText(
                    this@MainActivity, BIN_NOT_FOUND_TOAST_TEXT,
                    Toast.LENGTH_SHORT
                ).show()
            }
            binding.progressBar.visibility = View.GONE
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    override fun onResume() {
        super.onResume()

        overridePendingTransition(0, 0)
        binding.textInput.text.clear()
    }
}