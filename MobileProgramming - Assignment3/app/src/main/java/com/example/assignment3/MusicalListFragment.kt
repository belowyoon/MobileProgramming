package com.example.assignment3

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment3.adapter.MusicalAdapter
import com.example.assignment3.databinding.FragmentMusicalListBinding
import com.example.assignment3.roomDB.Musical
import com.example.assignment3.roomDB.MusicalDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class MusicalListFragment : Fragment() {

    var binding: FragmentMusicalListBinding? = null

    val absurl = "http://ticket.interpark.com/TPGoodsList.asp?Ca=Mus&Sort=2"
    val goodsurl = "https://tickets.interpark.com/goods/"
    val scope = CoroutineScope(Dispatchers.IO)

    lateinit var db: MusicalDatabase


    var musicalList = ArrayList<Musical>()
    var adapter = MusicalAdapter(ArrayList<Musical>())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMusicalListBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMusicalData()
        init()
    }

    private fun init() {
        db = MusicalDatabase.getDatabase(requireActivity())
        binding!!.apply {

            swipe.setOnRefreshListener {
                swipe.isRefreshing = true
                scope.launch {
                    db.musicalDao().deleteMusical()
                }
                getMusicalData()
            }
            recyclerView.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = MusicalAdapter(musicalList)
            adapter.itemClickListener = object : MusicalAdapter.OnItemClickListener {
                override fun onItemClick(url: String) {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(goodsurl + url.substring(url.length - 8, url.length))
                    )
                    startActivity(intent)
                }
            }
            recyclerView.adapter = adapter
        }
        scope.launch {
            getAllMusical()
        }
    }

    fun getMusicalData() {
        scope.launch {
            var count = db.musicalDao().getCount()
            Log.d("main", count.toString())
            if (count == 0) {
                Log.d("main", count.toString())
                val doc = Jsoup.connect(absurl).get()
                //Log.i("testtt", doc.toString())
                val musicals =
                    doc.select("div.stit>table>tbody>tr")
                //Log.i("test",musicals.toString())
                var num = 1
                for (musical in musicals) {
                    //Log.i("check1", musical.select(".RKtxt>.fw_bold>a").attr("href").toString())
                    insert(
                        Musical(
                            num,
                            musical.select(".RKtxt").text(),
                            musical.select(".RkDate + .RkDate").text(),
                            musical.select(".RkDate>a").text(),
                            musical.select(".RKtxt>.fw_bold>a").attr("href").toString()
                        )
                    )
                    num += 1
                }
            }
            withContext(Dispatchers.Main) {
                adapter.notifyDataSetChanged()
                binding!!.swipe.isRefreshing = false
            }
        }
    }

    fun insert(musical: Musical) {
        db.musicalDao().insertMusical(musical)
        getAllMusical()
    }

    fun getAllMusical() {
        musicalList = db.musicalDao().getAllMusical() as ArrayList<Musical>
        adapter.items = musicalList
        CoroutineScope(Dispatchers.Main).launch {
            adapter.notifyDataSetChanged()
        }
    }
}