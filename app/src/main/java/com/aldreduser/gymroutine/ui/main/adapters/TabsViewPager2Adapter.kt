package com.aldreduser.gymroutine.ui.main.adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aldreduser.gymroutine.ui.main.activities.MainActivity
import com.aldreduser.gymroutine.ui.main.fragments.WorkoutsGroupListFragment
import com.aldreduser.gymroutine.ui.main.viewmodels.WorkoutsListViewModel
import com.aldreduser.gymroutine.utils.MY_LOG

class TabsViewPager2Adapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val thisHolderActivity = MainActivity()
    private val listsViewModel = thisHolderActivity.getViewModel()
    private val tabTitles = listsViewModel.tabTitles
    private val tabTitlesOrdinals = listsViewModel.tabTitlesOrdinals

    override fun createFragment(position: Int): Fragment {
        return WorkoutsGroupListFragment.getInstance(tabTitles.size-1)
    }

    override fun getItemCount(): Int {
        return listsViewModel.tabTitles.size
    }

    override fun getItemId(position: Int): Long {
        return tabTitlesOrdinals[tabTitles[position]]!!.toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        var thisTitle = "No Title"
        tabTitlesOrdinals.forEach{ (k,v) ->
            if(v == itemId.toInt()) {
                thisTitle = k
            }
        }
        return tabTitles.contains(thisTitle)
    }

    fun addTab(ordinal: Int, title: String) {
        tabTitles.add(title)

        // don't rewrite an ordinal
        if(!tabTitlesOrdinals.containsKey(title)) {
            tabTitlesOrdinals[title] = ordinal
        }
        notifyDataSetChanged()
        Log.d("${MY_LOG}created", "\t\t\t $tabTitles")
        Log.d("${MY_LOG}created", "\t\t\t $tabTitlesOrdinals")
    }

    fun removeTab(title: String) {
        tabTitles.remove(title)
        notifyDataSetChanged()
        Log.d("${MY_LOG}removeTab", "----------------")
    }
}
