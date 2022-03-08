package com.aldreduser.gymroutine.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.aldreduser.gymroutine.data.model.entities.WorkoutGroup
import com.aldreduser.gymroutine.databinding.FragmentWorkoutListBinding
import com.aldreduser.gymroutine.ui.main.adapters.WorkoutListAdapter
import com.aldreduser.gymroutine.ui.main.viewmodel.WorkoutListViewModel
import com.aldreduser.gymroutine.utils.FIRST_TAB_TITLE

class WorkoutListFragment : Fragment() {

    private val fragmentTAG = "Workout List FragmentTAG"
    private var binding: FragmentWorkoutListBinding? = null
    private val viewModel: WorkoutListViewModel by activityViewModels()
    private lateinit var recyclerAdapter: WorkoutListAdapter
    private var groupToDisplay = FIRST_TAB_TITLE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentWorkoutListBinding.inflate(inflater, container,false)
        binding = fragmentBinding
        recyclerAdapter = WorkoutListAdapter(viewModel, requireContext(), viewLifecycleOwner)
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            workoutListRecycler.adapter = recyclerAdapter
            workoutListRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
        }
        setObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.currentGroup = groupToDisplay
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setObservers() {
        viewModel.workouts.observe(viewLifecycleOwner) {
            if(groupToDisplay == FIRST_TAB_TITLE) {
                recyclerAdapter.submitList(it)
            } else {
                val groupedWorkouts = viewModel.getWorkoutsOfGroup(WorkoutGroup(groupToDisplay))
                recyclerAdapter.submitList(groupedWorkouts)
            }
        }
    }

    companion object{
        fun getInstance(titleToDisplay: String): WorkoutListFragment {
            val thisFragment = WorkoutListFragment()
            thisFragment.groupToDisplay = titleToDisplay
            return thisFragment
        }
    }
}