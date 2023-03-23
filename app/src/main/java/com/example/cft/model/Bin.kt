package com.example.cft.model

@kotlinx.serialization.Serializable
data class Bin(
    val number: BinNumber = BinNumber(),
    val scheme: String? = null,
    val type: String? = null,
    val brand: String? = null,
    val prepaid: Boolean? = null,
    val country: BinCountry = BinCountry(),
    val bank: BinBank = BinBank()
)

@kotlinx.serialization.Serializable
data class BinNumber(
    val length: Int? = null,
    val luhn: Boolean? = null
)

@kotlinx.serialization.Serializable
data class BinCountry(
    val numeric: Int? = null,
    val alpha2: String? = null,
    val name: String? = null,
    val emoji: String? = null, // TODO(Сделать emoji)
    val currency: String? = null,
    val latitude: Int? = null,
    val longitude: Int? = null
)

@kotlinx.serialization.Serializable
data class BinBank(
    val name: String? = null,
    val url: String? = null,
    val phone: String? = null,
    val city: String? = null
)