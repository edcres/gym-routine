package com.aldreduser.gymroutine.ui.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter

// ViewPager2 uses a recyclerView (this is the recyclerView Adapter)

class MainViewPager2Adapter (fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun createFragment(position: Int): Fragment {
        TODO("Not yet implemented")
    }

//    inner class ViewPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(, parent, false)    // inflate the fragment in the first parameter here
//    }
//
//    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
//    }
//
//    override fun getItemCount(): Int {
//    }
}