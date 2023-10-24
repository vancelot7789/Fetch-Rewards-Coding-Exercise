package com.example.fetchrewardscodingexercise.Controller
import ItemAdapter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fetchrewardscodingexercise.Model.Item
import com.example.fetchrewardscodingexercise.R
import com.example.fetchrewardscodingexercise.Service.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://fetch-hiring.s3.amazonaws.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.fetchItems()

        call.enqueue(object : Callback<List<Item>> {
            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                val items = response.body()!!
                processData(items)
            }

            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Failed to fetch data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun processData(items: List<Item>) {
        val filteredItems = items.filter { !it.name.isNullOrBlank() }
        val groupedItems = filteredItems.groupBy { it.listId }
        val sortedGroupedItems: Map<Int, List<Item>> = groupedItems.mapValues {
            it.value.sortedBy { item -> item.name }
        }

        val flattenedList: List<Item> = sortedGroupedItems.values.flatten().sortedWith(
            compareBy({ it.listId }, { it.name })
        )

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        val itemAdapter = ItemAdapter(flattenedList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = itemAdapter
    }
}
