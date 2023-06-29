package com.example.assignment3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.assignment3.adapter.MusicalViewPagerAdapter
import com.example.assignment3.databinding.ActivityMainBinding
import com.example.assignment3.roomDB.MusicalDatabase
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    val textArr =  arrayListOf<String>("랭킹", "검색")

    var musicalViewPagerAdapter = MusicalViewPagerAdapter(this)

    lateinit var db:MusicalDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MusicalDatabase.getDatabase(this)
        initLayout()
    }
    private fun initLayout() {

        binding.viewpager.adapter = musicalViewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewpager) {
                tab,pos ->
            tab.text = textArr[pos]
            //tab.setIcon(imgArr[pos])
        }.attach()
    }

}
