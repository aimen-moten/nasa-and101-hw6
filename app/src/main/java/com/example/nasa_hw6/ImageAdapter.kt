package com.example.nasa_hw6

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ImageAdapter (private val imageList: List<String>, private val idList: List<String>, private val camList: List<String>): RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val roverImage: ImageView
        val id: TextView
        val cam: TextView

        init {
            roverImage = view.findViewById(R.id.imageRover)
            id = view.findViewById(R.id.name)
            cam = view.findViewById(R.id.camName)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rover_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.roverImage.setOnClickListener{
            Toast.makeText(holder.itemView.context, "Clicked on ${position}th Image", Toast.LENGTH_SHORT).show()
        }
        holder.id.text = idList[position]
        holder.cam.text = camList[position]
        Glide.with(holder.itemView)
            .load(imageList[position])
            .centerCrop()
            .into(holder.roverImage)
    }
}