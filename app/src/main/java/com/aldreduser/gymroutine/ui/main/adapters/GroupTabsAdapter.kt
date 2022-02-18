package com.aldreduser.gymroutine.ui.main.adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aldreduser.gymroutine.ui.main.fragments.WorkoutListFragment
import com.aldreduser.gymroutine.ui.main.viewmodel.WorkoutListViewModel

class GroupTabsAdapter(
    fragment: Fragment,
    private val listsViewModel: WorkoutListViewModel
    ) : FragmentStateAdapter(fragment) {

    private val TAG = "Tabs Adapter TAG"
    private val tabTitles = listsViewModel.tabTitles
    private val tabTitlesOrdinals = listsViewModel.tabTitlesOrdinals

    // todo: what is this?
    override fun createFragment(position: Int): Fragment {
        return WorkoutListFragment.getInstance(tabTitles.size-1)
    }

    override fun getItemCount(): Int {
        return listsViewModel.tabTitles.size
    }

    override fun getItemId(position: Int): Long {
        return tabTitlesOrdinals[tabTitles[position]]!!.toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        var thisTitle = "No Title"
        tabTitlesOrdinals.forEach { (k,v) ->
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
        Log.d(TAG, "created\t\t\t $tabTitles")
        Log.d(TAG, "created\t\t\t $tabTitlesOrdinals")
    }

    fun removeTab(title: String) {
        tabTitles.remove(title)
        notifyDataSetChanged()
        Log.d(TAG, "remove tab:  ----------------")
    }
}
