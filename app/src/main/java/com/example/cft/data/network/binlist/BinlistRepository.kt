package com.example.cft.data.network.binlist

import android.util.Log
import com.example.cft.data.network.BinResponse
import com.example.cft.data.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class BinlistRepository {

    private val api = Network.getBinlistApi()

    suspend fun getBinlist(bin: Int): Flow<Result<BinResponse>> = flow{
        try {
            val data = api.GetBin(bin)
            emit(Result.success(data))
        } catch (e: Exception) {
            Log.e("response error", e.message.toString())
            emit(Result.failure(Throwable(e)))
        }
    }.flowOn(Dispatchers.IO)
}