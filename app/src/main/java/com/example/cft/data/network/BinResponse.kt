package com.example.cft.data.network

@kotlinx.serialization.Serializable
data class BinResponse(
    val number: BinNumber,
    val scheme: String,
    val type: String,
    val brand: String,
    val prepaid: Boolean,
    val country: BinCountry,
    val bank: BinBank
)

@kotlinx.serialization.Serializable
data class BinNumber(
    val length: Int,
    val luhn: Boolean
)

@kotlinx.serialization.Serializable
data class BinCountry(
    val numeric: Int,
    val alpha2: String,
    val name: String,
    val emoji: String, // TODO(Сделать emoji)
    val currency: String,
    val latitude: Int,
    val longitude: Int
)

@kotlinx.serialization.Serializable
data class BinBank(
    val name: String,
    val url: String,
    val phone: String,
    val city: String
)