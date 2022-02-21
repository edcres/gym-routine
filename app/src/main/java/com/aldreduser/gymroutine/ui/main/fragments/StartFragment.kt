package com.aldreduser.gymroutine.ui.main.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.aldreduser.gymroutine.databinding.FragmentStartBinding
import com.aldreduser.gymroutine.ui.main.adapters.GroupTabsAdapter
import com.aldreduser.gymroutine.ui.main.viewmodel.WorkoutListViewModel
import com.aldreduser.gymroutine.utils.findDifferentGroup
import com.aldreduser.gymroutine.utils.findDifferentName
import com.google.android.material.tabs.TabLayoutMediator

class StartFragment : Fragment() {

    private val fragmentTAG = "StartFragmentTAG"
    private var binding: FragmentStartBinding? = null
    private val workoutsViewModel: WorkoutListViewModel by activityViewModels()
    private lateinit var groupTabsAdapter: GroupTabsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentStartBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            addWorkoutFab.setOnClickListener { addWorkout() }
        }
        groupTabsAdapter = GroupTabsAdapter(this, workoutsViewModel)
        workoutsViewModel.startApplication(requireNotNull(this.activity).application)
        setUpAppBar()
        setUpTabs()
        setObservers()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun addTab() {
//        workoutsViewModel.addTab(titleToAdd = , groupTabsAdapter)
    }
    private fun removeTab() {
//        workoutsViewModel.removeTab(titleToRemove = , groupTabsAdapter)
    }

    // CLICK HANDLERS //
    private fun addWorkout() {
        // add workout
        // todo: handle click
    }

    // SETUP //
    private fun setUpAppBar() {
        binding?.mainActivityTopAppbar?.title = "Workouts"
    }

    private fun setUpTabs() {
        binding?.apply {
            workoutsListViewPager2.adapter = groupTabsAdapter
            TabLayoutMediator(
                mainTabLayout,
                workoutsListViewPager2
            ) { tab, position ->
                tab.text = workoutsViewModel.groupNames[position]
            }.attach()
        }
    }

    private fun setObservers() {
        workoutsViewModel.groups.observe(viewLifecycleOwner) {
            when {
                workoutsViewModel.groups.value!!.size > workoutsViewModel.groupNames.size -> {
                    // new group was added
                    val newGroupName = findDifferentGroup(it, workoutsViewModel.groupNames)
                    workoutsViewModel.addTab(newGroupName, groupTabsAdapter)
                }
                workoutsViewModel.groups.value!!.size < workoutsViewModel.groupNames.size -> {
                    // a group was removed
                    val removedGroupName = findDifferentName(workoutsViewModel.groupNames, it)
                    workoutsViewModel.removeTab(removedGroupName, groupTabsAdapter)
                }
                else -> {
                    Log.i(fragmentTAG, "setObservers: no group was added or removed")
                }
            }
        }
    }
    // SETUP //
}