package com.example.recyclerview
import android.content.Context;
import android.media.Image
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter
import android.widget.ImageView


public class EmotionCustomAdapter(val context: Context, val items: Array<Int> ): BaseAdapter() {

    var inflater: LayoutInflater = (LayoutInflater.from(context))

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(p0: Int): Any? {
        return null;
    }

    override fun getItemId(p0: Int): Long {
        return 0;
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup?): View {
        val view = inflater.inflate(R.layout.imagespinner_row,null)
        val icon = view.findViewById<ImageView>(R.id.imageView)
        icon.setImageResource(items[i])
        return view
    }

}