package com.example.cft.data.network.binlist

import com.example.cft.data.network.BinResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BinlistApi {

    @GET("{bin}")
    suspend fun GetBin(@Path("bin") bin: Int): BinResponse
}