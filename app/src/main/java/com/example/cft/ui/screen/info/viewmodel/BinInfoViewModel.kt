package com.example.cft.ui.screen.info.viewmodel

import android.content.Context
import android.content.Intent
import android.net.Uri
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

    fun openMaps(ctx: Context) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("geo:${binData.country.latitude},${binData.country.longitude}")
        )
        ctx.startActivity(intent)
    }

    fun openPhone(ctx: Context) {
        val intent = Intent(
            Intent.ACTION_DIAL,
            Uri.parse("tel:${binData.bank.phone}")
        )
        ctx.startActivity(intent)
    }

    fun openBrowser(ctx: Context) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://${binData.bank.url}")
        )
        ctx.startActivity(intent)
    }

}