package com.example.cft.ui.screen.main.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cft.data.network.binlist.BinlistRepository
import com.example.cft.ui.screen.info.view.BinInfoActivity
import com.example.cft.ui.screen.main.adapters.BinHistoryAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val binlistRepository = BinlistRepository()

class MainViewModel : ViewModel() {

    fun findBinInfo(bin: String, ctx: Context, adapter: BinHistoryAdapter) {
        viewModelScope.launch(Dispatchers.IO) {
            binlistRepository.getBinlist(bin).collect { result ->
                result.onSuccess {
                    val intent = Intent(ctx, BinInfoActivity::class.java)
                    intent.putExtra("BinData", Json.encodeToString(it))
                    ctx.startActivity(intent)


                    delay(500)
                    launch(Dispatchers.Main) {
                        adapter.data = listOf(bin) + (adapter.data - bin)
                    }
                }.onFailure {
                    // TODO(Показывать, что не удалось получить данные)
                }
            }
        }
    }
}