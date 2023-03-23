package com.example.cft.ui.screen.main.viewmodel

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import com.example.cft.data.network.binlist.BinlistRepository
import com.example.cft.model.Bin
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val binlistRepository = BinlistRepository()

private const val APP_PREFERENCES = "preferences_settings"
private const val APP_PREFERENCES_BIN_LIST = "binlist"

class MainViewModel(application: Application) : AndroidViewModel(application) {

    fun loadBinHistory(): List<String> {
        val preferences: SharedPreferences =
            getApplication<Application>().getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        val data = preferences.getString(APP_PREFERENCES_BIN_LIST, null)

        return if (data == null)
            listOf()
        else
            Json.decodeFromString(data)
    }

    fun updateBinHistory(data: List<String>) {
        val preferences: SharedPreferences =
            getApplication<Application>().getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(APP_PREFERENCES_BIN_LIST, Json.encodeToString(data))
        editor.apply()
    }

    fun clearBinHistory() {
        updateBinHistory(listOf())
    }

    suspend fun findBinInfo(bin: String): Bin? {
        var res: Bin? = null

        binlistRepository.getBinlist(bin).collect { result ->
            result.onSuccess {
                res = it
            }.onFailure {
                res = null
            }
        }

        return res
    }
}