package com.example.dictionaryretrofit.retrofit


import com.example.dictionaryretrofit.Constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClient{

    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit? {
        retrofit = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }
}
