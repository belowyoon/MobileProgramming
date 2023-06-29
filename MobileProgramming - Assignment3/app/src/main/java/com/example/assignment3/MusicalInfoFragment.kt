package com.example.assignment3

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment3.adapter.MusicalInfoAdapter
import com.example.assignment3.adapter.MusicalViewPagerAdapter
import com.example.assignment3.databinding.FragmentMusicalInfoBinding
import com.example.assignment3.roomDB.Musical
import com.example.assignment3.roomDB.MusicalDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.log

class MusicalInfoFragment : Fragment() {

    var binding: FragmentMusicalInfoBinding?=null

    val goodsurl = "https://tickets.interpark.com/goods/"
    val scope = CoroutineScope(Dispatchers.IO)

    lateinit var db: MusicalDatabase

    var musicalList = ArrayList<Musical>()
    var adapter = MusicalInfoAdapter(ArrayList<Musical>())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMusicalInfoBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycelerView()
        initLayout()
    }

    private fun initRecycelerView() {
        db = MusicalDatabase.getDatabase(requireActivity())
        binding!!.apply {
            recyclerView.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = MusicalInfoAdapter(musicalList)
            adapter.itemClickListener = object: MusicalInfoAdapter.OnItemClickListener {
                override fun onItemClick(url: String) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(goodsurl + url.substring(url.length-8, url.length)))
                    startActivity(intent)
                }
            }
            recyclerView.adapter = adapter
        }
    }

    fun initLayout() {
        binding!!.apply {
            findBtn.setOnClickListener {
                var name = nameEdit.text.toString()
                name = "%" + name + "%"
                scope.launch {
                    findMusical(name)
                }
            }
            findBtn2.setOnClickListener {
                var place = placeEdit.text.toString()
                place = "%" + place + "%"
                scope.launch {
                    findMusicalByPlace(place)
                }
            }
        }
    }

    fun findMusical(name:String){
        musicalList = db.musicalDao().findMusical(name) as ArrayList<Musical>
        adapter.items = musicalList
        CoroutineScope(Dispatchers.Main).launch {
            adapter.notifyDataSetChanged()
        }
    }

    fun findMusicalByPlace(place:String){
        musicalList = db.musicalDao().findMusicalByPlace(place) as ArrayList<Musical>
        adapter.items = musicalList
        CoroutineScope(Dispatchers.Main).launch {
            adapter.notifyDataSetChanged()
        }
    }

}
