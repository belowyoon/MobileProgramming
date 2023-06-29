package com.example.photo.picture

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.photo.databinding.ImageRowBinding
import com.example.photo.picture.model.ImageData

class PictureAdapter(val imageDataList: ArrayList<ImageData>): RecyclerView.Adapter<PictureAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ImageRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageData: ImageData) {
           Glide.with(binding.root)
               .load(imageData.uri)
               .centerCrop()
               .into(binding.rowImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ImageRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imageDataList[position])
    }

    override fun getItemCount(): Int = imageDataList.size

}