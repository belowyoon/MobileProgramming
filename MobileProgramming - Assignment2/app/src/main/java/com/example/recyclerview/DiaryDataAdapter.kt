package com.example.recyclerview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.databinding.RowBinding


class DiaryDataAdapter (val diarys:ArrayList<DiaryData>, var memoList: ArrayList<String>)
    : RecyclerView.Adapter<DiaryDataAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun OnItemClick(data: DiaryData, memo: String)
    }

    var itemClickListener:OnItemClickListener?= null

    inner class ViewHolder (val binding: RowBinding):RecyclerView.ViewHolder(binding.root){
        init {
            binding.row.setOnClickListener{
                itemClickListener?.OnItemClick(diarys[adapterPosition],memoList[adapterPosition])
            }
        }
    }

    fun moveItem(oldPos: Int, newPos: Int) {
        diarys[oldPos] = diarys[newPos].also {diarys[newPos] = diarys[oldPos]}
        memoList[oldPos] = memoList[newPos].also {memoList[newPos] = memoList[oldPos]}
        notifyItemMoved(oldPos,newPos)
    }

    fun removeItem(pos: Int) {
        diarys.removeAt(pos)
        memoList.removeAt(pos)
        notifyItemRemoved(pos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RowBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.image.setImageResource(diarys[position].emotion)
        holder.binding.date.text = diarys[position].date
        holder.binding.activity.text = diarys[position].activity
        holder.binding.row.setBackgroundResource(diarys[position].color)
        memoList.add("")
        holder.binding.memo.setText(memoList[position])
        holder.binding.memo.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int, count: Int) {
                memoList[holder.adapterPosition] = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    override fun getItemCount(): Int {
        return diarys.size
    }

}