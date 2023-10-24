package com.example.fetchrewardscodingexercise.Service

import com.example.fetchrewardscodingexercise.Model.Item
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("hiring.json")
    fun fetchItems(): Call<List<Item>>
}
