package com.example.cft.data.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class MyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request: Request = chain.request().newBuilder().apply {
            addHeader("accept", "application/json")
        }.build()

        var response: Response? = null

        return try{
            response = chain.proceed(request)
            Log.d("response", response.message)
            response
        } catch (e: Exception){
            response?.close()
            chain.proceed(request)
        }
    }
}