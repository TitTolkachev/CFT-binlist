package com.example.cft.data.network.binlist

import com.example.cft.model.Bin
import retrofit2.http.GET
import retrofit2.http.Path

interface BinlistApi {

    @GET("{bin}")
    suspend fun getBin(@Path("bin") bin: String): Bin
}