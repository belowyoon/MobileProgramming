package com.example.assignment3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment3.databinding.RowBinding
import com.example.assignment3.roomDB.Musical

class MusicalAdapter(var items: ArrayList<Musical>)
    :RecyclerView.Adapter<MusicalAdapter.MyViewHolder>()
{
    interface OnItemClickListener {
        fun onItemClick(url: String)
    }

    var itemClickListener: OnItemClickListener?= null

    inner class MyViewHolder(val binding: RowBinding):RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(items[adapterPosition].url)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = RowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.musicalId.text = items[position].pId.toString()
        holder.binding.musicalTitle.text = items[position].name
        holder.binding.musicalDate.text = items[position].date
        holder.binding.musicalPlace.text = items[position].place
    }
}
