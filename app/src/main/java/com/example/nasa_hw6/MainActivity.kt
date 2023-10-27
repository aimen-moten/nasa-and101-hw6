package com.example.nasa_hw6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {
    private var roverURL = mutableListOf<String>()
    private var ids = mutableListOf<String>()
    private var cameras = mutableListOf<String>()
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.rvImages)
        getRoverImage()
    }

    private fun getRoverImage() {
        val client = AsyncHttpClient()
        val apiKey = "v7EzRNt7IsJ9ppRUCAyVdO7HvxbqkOTAAvQUQP4e"
        client.get("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=700&api_key=$apiKey", object: JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                val photos = json.jsonObject.getJSONArray("photos")
                Log.d("resp", "$photos")
                for (i in 0 until photos.length()) {
                    var url = photos.getJSONObject(i).getString("img_src")
                    //adding s in http: to make it https
                    val firstHalf = url.substring(0,4) + "s"
                    url = firstHalf + url.substring(4)
                    val id = photos.getJSONObject(i).getInt("id")
                    val cam = photos.getJSONObject(i).getJSONObject("camera").getString("name")
                    roverURL.add(url)
                    ids.add("ID: " + id)
                    cameras.add("Camera: " + cam)
                }
                val adapter =ImageAdapter(roverURL, ids, cameras)
                recyclerView.adapter = adapter
                recyclerView.layoutManager =LinearLayoutManager(this@MainActivity)
                val itemDecoration = DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL)
                getDrawable(R.drawable.divider)?.let { itemDecoration.setDrawable(it) }
                recyclerView.addItemDecoration(itemDecoration)
                Log.d("sucess", "got the data$roverURL")
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String,
                throwable: Throwable?

            ) {
                Log.d("failure", "$response")
            }
        })


    }
}