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
    private val viewModel: WorkoutListViewModel by activityViewModels()
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
        groupTabsAdapter = GroupTabsAdapter(this, viewModel)
        viewModel.startApplication(requireNotNull(this.activity).application)
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
        //  add a workout item to the recyclerView


        //todo:
        // below will be called when the user types the first letter of the workout name
        // it will be called in the adapter
//        viewModel.insertWorkout(Workout(
//            thisWorkoutName = specificWorkoutInput.text.toString(),
//            workoutGroup = if(viewModel.currentGroup != FIRST_TAB_TITLE) {viewModel.currentGroup} else ""
//            // 'sets' is one by default in the viewModel
//        ))
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
                tab.text = viewModel.groupNames[position]
            }.attach()
        }
    }

    private fun setObservers() {
        // todo: i think i need to use this observer to set the tabs in the beginning
        // Observer for adding or removing tabs
        viewModel.groups.observe(viewLifecycleOwner) {
            when {
                // '+1' because groupNames start out with FIRST_TAB_TITLE
                viewModel.groups.value!!.size+1 > viewModel.groupNames.size -> {
                    // new group was added
                    val newGroupName = findDifferentGroup(it, viewModel.groupNames)
                    viewModel.addTab(newGroupName, groupTabsAdapter)
                }
                viewModel.groups.value!!.size+1 < viewModel.groupNames.size -> {
                    // a group was removed
                    val removedGroupName = findDifferentName(viewModel.groupNames, it)
                    viewModel.removeTab(removedGroupName, groupTabsAdapter)
                }
                else -> {
                    Log.i(fragmentTAG, "setObservers: no group was added or removed")
                }
            }
        }
    }
    // SETUP //
}