package com.aldreduser.gymroutine.ui.main.adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aldreduser.gymroutine.ui.main.fragments.WorkoutListFragment
import com.aldreduser.gymroutine.ui.main.viewmodel.WorkoutListViewModel
import com.aldreduser.gymroutine.utils.GLOBAL_TAG

class GroupTabsAdapter(
    fragment: Fragment,
    private val viewModel: WorkoutListViewModel
) : FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        return WorkoutListFragment.getInstance(viewModel.groupNames.last())
    }

    override fun getItemCount(): Int {
        return viewModel.groupNames.size
    }

    override fun getItemId(position: Int): Long {
        return viewModel.groupsOrdinals[viewModel.groupNames[position]]!!.toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        var thisTitle = ""
        viewModel.groupsOrdinals.forEach { (k,v) ->
            if(v == itemId.toInt()) {
                thisTitle = k
            }
        }
        return viewModel.groupNames.contains(thisTitle)
    }

    fun addTab(ordinal: Int, title: String) {
        viewModel.groupNames.add(title)

        if(!viewModel.groupsOrdinals.containsKey(title)) {
            viewModel.groupsOrdinals[title] = ordinal
        }
        notifyItemInserted(ordinal)
    }

    fun removeTab(ordinal: Int, title: String) {
        viewModel.groupNames.remove(title)
        viewModel.groupsOrdinals.remove(title)
        notifyItemRemoved(ordinal)
    }
}
