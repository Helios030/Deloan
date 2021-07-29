package com.neutron.deloan.net


import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

abstract class BaseRetrofitClient {
    companion object {
        private const val TIME_OUT =60L
    }

    private val client: OkHttpClient
        get() {
            return OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build()
        }

    fun <S> getService(clazz: Class<S>, baseUrl: String): S {
        return Retrofit.Builder()
            .client(client)
//            .addCallAdapterFactory(CoroutineCallAdapterFactory())
//            .addConverterFactory( NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build().create(clazz)
    }



}