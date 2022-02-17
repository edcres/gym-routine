package com.aldreduser.gymroutine.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.aldreduser.gymroutine.data.WorkoutRepository
import com.aldreduser.gymroutine.data.model.room.WorkoutsRoomDatabase
import com.aldreduser.gymroutine.databinding.FragmentStartBinding
import com.aldreduser.gymroutine.ui.main.viewmodel.WorkoutListViewModel
import com.aldreduser.gymroutine.ui.main.viewmodel.WorkoutListViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class StartFragment : Fragment() {

    private var binding: FragmentStartBinding? = null
    private lateinit var workoutListViewModel: WorkoutListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentStartBinding
            .inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModel()
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            addWorkoutFab.setOnClickListener { fabOnClick() }
        }
        setUpAppBar()
        setUpTabs()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    // CLICK HANDLERS //
    private fun fabOnClick() {
        // add workout
        // todo: handle click
    }

    // SETUP FUNCTIONS //
    private fun setUpViewModel() {
        val application = requireActivity().application
        val database = WorkoutsRoomDatabase.getInstance(requireContext())    // maybe not 'this', 'application' instead
        val repository = WorkoutRepository.getInstance(database)
        val viewModelFactory = WorkoutListViewModelFactory(repository, application)
        workoutListViewModel = ViewModelProvider(
            this, viewModelFactory).get(WorkoutListViewModel::class.java)
    }

    private fun setUpAppBar() {
        binding?.mainActivityTopAppbar?.title = "Workouts"

        binding?.mainActivityTopAppbar?.setNavigationOnClickListener {
            // todo: handle navigation icon press
        }
    }

    private fun setUpTabs() {
        workoutListViewModel.setViewPager2Adapter(requireContext())
        binding!!.workoutsListViewPager2.adapter = workoutListViewModel.getViewPager2Adapter()

        TabLayoutMediator(binding!!.mainTabLayout, binding!!.workoutsListViewPager2) { tab, position ->
            tab.text = workoutListViewModel.tabTitles[position]
        }.attach()
    }
}