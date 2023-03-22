package com.example.cft.ui.screen.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cft.data.network.binlist.BinlistRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private val binlistRepository = BinlistRepository()

class MainViewModel : ViewModel() {

    fun findBinInfo(bin: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            binlistRepository.getBinlist(bin).collect()
        }
    }
}