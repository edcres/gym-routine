package com.aldreduser.gymroutine.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.aldreduser.gymroutine.databinding.FragmentStartBinding
import com.aldreduser.gymroutine.ui.main.viewmodel.WorkoutListViewModel
import com.google.android.material.tabs.TabLayoutMediator

class StartFragment : Fragment() {

    private val fragmentTAG = "StartFragmentTAG"
    private var binding: FragmentStartBinding? = null
    private val workoutListViewModel: WorkoutListViewModel by activityViewModels()

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
        workoutListViewModel.startApplication(requireNotNull(this.activity).application)


        setUpAppBar()
        setUpTabs()
        setObservers()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
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
        workoutListViewModel.setViewPager2Adapter(requireContext())
        binding?.apply {
            workoutsListViewPager2.adapter = workoutListViewModel.getViewPager2Adapter()
            TabLayoutMediator(
                mainTabLayout,
                workoutsListViewPager2
            ) { tab, position ->
                tab.text = workoutListViewModel.groupNames[position]
            }.attach()
        }
    }

    private fun setObservers() {
        // todo:
    }
    // SETUP //
}