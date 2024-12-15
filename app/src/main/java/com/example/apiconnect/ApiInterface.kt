package com.example.apiconnect

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
//retrofit sends the data into a java/ kotlin interface so we need to make a inteface and use a function to
//    fetch the data received by api
    @GET("products")
    fun getProductData(): Call<MyData>
}