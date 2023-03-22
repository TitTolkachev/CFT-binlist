package com.example.cft.ui.screen.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cft.databinding.ActivityMainBinding
import com.example.cft.ui.screen.main.adapters.BinHistoryAdapter
import com.example.cft.ui.screen.main.adapters.BinItemActionListener
import com.example.cft.ui.screen.main.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    lateinit var adapter: BinHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.button.setOnClickListener {
            viewModel.findBinInfo(binding.textInput.text.toString().toInt(), this, adapter)
        }

        val linearLayoutManager = LinearLayoutManager(this)
        binding.binHistoryList.layoutManager = linearLayoutManager

        adapter = BinHistoryAdapter(object : BinItemActionListener {
            override fun onItemClicked(bin: Int) = viewModel.findBinInfo(bin, this@MainActivity, adapter)
        })
        adapter.data = listOf(45717360, 45717361) // TODO(Сюда прикрутить получение номеров)
        binding.binHistoryList.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        binding.textInput.text.clear()
    }
}