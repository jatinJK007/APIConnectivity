package com.example.apiconnect

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView : RecyclerView
    lateinit var myAdapter: MyAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // here we have created a builder for retrofit to act

        val gson = GsonBuilder().setLenient().create()

        recyclerView= findViewById(R.id.recyclerView)
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiInterface::class.java)



        //to receive data given by retrofit we have created a variable to access tha data
        val retrofitData = retrofitBuilder.getProductData()

        retrofitData.enqueue(object : Callback<MyData?> {

            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                Log.d("TAG", "onResponse: ")
                //if call is success then this method will call
                var responseBody = response.body()
                var productList = responseBody?.products?:emptyList()

                //    we have added ? if there is a null value from the api then the function will not get called further
                //  val collectDataInSB = StringBuilder()
                //  for(myData in productList){
                //      collectDataInSB.append(myData.title + "  . .  " )
                //  }
                //  val tv = findViewById<TextView>(R.id.textView)
                //  tv.text = collectDataInSB

                myAdapter = MyAdapter(this@MainActivity, productList)
                recyclerView.adapter = myAdapter
                recyclerView.layoutManager= LinearLayoutManager(this@MainActivity)
            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                Log.d("MainActivityonfailure", "onFailure: " +t.message)
            }
        })

    }
}