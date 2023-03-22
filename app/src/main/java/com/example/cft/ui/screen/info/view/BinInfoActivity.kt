package com.example.cft.ui.screen.info.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.cft.databinding.ActivityBinInfoBinding
import com.example.cft.ui.screen.info.viewmodel.BinInfoViewModel

class BinInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBinInfoBinding
    private lateinit var viewModel: BinInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[BinInfoViewModel::class.java]

        binding = ActivityBinInfoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel.loadBinData(intent)
    }
}