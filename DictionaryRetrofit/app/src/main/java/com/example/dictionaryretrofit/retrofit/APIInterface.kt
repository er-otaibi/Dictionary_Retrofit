package com.example.dictionaryretrofit.retrofit

import com.example.dictionaryretrofit.json.MyDictionary
import com.example.dictionaryretrofit.json.MyDictionaryItem
import retrofit2.Call
import retrofit2.http.*

interface APIInterface{

    @Headers("Content-Type: application/json")
    @GET()
    fun getData(@Url url: String): Call<List<MyDictionaryItem>>

}
