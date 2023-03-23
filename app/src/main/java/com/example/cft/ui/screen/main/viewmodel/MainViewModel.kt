package com.example.cft.ui.screen.main.viewmodel

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
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

class MainViewModel(application: Application) : AndroidViewModel(application) {

    fun loadBinHistory(): List<String> {
        val preferences: SharedPreferences = getApplication<Application>().getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        val data = preferences.getString(APP_PREFERENCES_BIN_LIST, null)

        return if (data == null)
            listOf()
        else
            Json.decodeFromString(data)
    }

    private fun updateBinHistory(data: List<String>) {
        val preferences: SharedPreferences = getApplication<Application>().getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(APP_PREFERENCES_BIN_LIST, Json.encodeToString(data))
        editor.apply()
    }

    fun clearBinHistory() {
        updateBinHistory(listOf())
    }

    fun findBinInfo(bin: String, adapter: BinHistoryAdapter) {
        viewModelScope.launch(Dispatchers.IO) {
            binlistRepository.getBinlist(bin).collect { result ->
                result.onSuccess {
                    val intent = Intent(getApplication(), BinInfoActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.putExtra("BinData", Json.encodeToString(it))
                    intent.putExtra("BinNumber", bin)
                    getApplication<Application>().startActivity(intent)

                    delay(500)
                    launch(Dispatchers.Main) {
                        adapter.data = listOf(bin) + (adapter.data - bin)
                        updateBinHistory(adapter.data)
                    }
                }.onFailure {
                    // TODO(Показывать, что не удалось получить данные)
                }
            }
        }
    }
}