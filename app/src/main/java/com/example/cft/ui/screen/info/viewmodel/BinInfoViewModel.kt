package com.example.cft.ui.screen.info.viewmodel

import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.cft.model.Bin
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


class BinInfoViewModel : ViewModel() {

    lateinit var binData: Bin

    fun loadBinData(intent: Intent) {
        val binDataString = intent.getStringExtra("BinData")
        if (binDataString != null)
            binData = Json.decodeFromString(binDataString)

        Log.d("BinData", binData.toString())
    }

}