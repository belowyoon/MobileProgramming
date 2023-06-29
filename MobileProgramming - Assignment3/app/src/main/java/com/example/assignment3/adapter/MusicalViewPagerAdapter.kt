package com.example.assignment3.adapter

import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.assignment3.MusicalInfoFragment
import com.example.assignment3.MusicalListFragment
import com.example.assignment3.roomDB.Musical

class MusicalViewPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when(position) {
            0->return MusicalListFragment()
            1->return MusicalInfoFragment()
            else->return MusicalListFragment()
        }
    }
}
