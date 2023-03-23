package com.example.cft.ui.screen.info.viewmodel

import android.content.Intent
import androidx.lifecycle.ViewModel
import com.example.cft.model.Bin
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class BinInfoViewModel : ViewModel() {

    lateinit var binData: Bin
    var binNumber: String? = null

    fun loadBinData(intent: Intent) {
        val binDataString = intent.getStringExtra("BinData")
        binNumber = intent.getStringExtra("BinNumber")
        if (binDataString != null)
            binData = Json.decodeFromString(binDataString)
    }

}