package com.example.cft.ui.screen.main.viewmodel

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cft.data.network.binlist.BinlistRepository
import com.example.cft.ui.screen.info.view.BinInfoActivity
import com.example.cft.ui.screen.main.adapters.BinHistoryAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val binlistRepository = BinlistRepository()

private const val APP_PREFERENCES = "preferences_settings"
private const val APP_PREFERENCES_BIN_LIST = "binlist"

class MainViewModel : ViewModel() {

    fun loadBinHistory(ctx: Context): List<String> {
        val preferences: SharedPreferences = ctx.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        val data = preferences.getString(APP_PREFERENCES_BIN_LIST, null)

        return if (data == null)
            listOf()
        else
            Json.decodeFromString(data)
    }

    private fun updateBinHistory(ctx: Context, data: List<String>) {
        val preferences: SharedPreferences = ctx.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(APP_PREFERENCES_BIN_LIST, Json.encodeToString(data))
        editor.apply()
    }

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
                        updateBinHistory(ctx, adapter.data)
                    }
                }.onFailure {
                    // TODO(Показывать, что не удалось получить данные)
                }
            }
        }
    }
}