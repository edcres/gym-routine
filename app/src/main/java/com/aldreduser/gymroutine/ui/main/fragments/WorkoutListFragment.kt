package com.aldreduser.gymroutine.ui.main.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.aldreduser.gymroutine.R
import com.aldreduser.gymroutine.databinding.FragmentWorkoutListBinding
import com.aldreduser.gymroutine.ui.main.adapters.WorkoutListAdapter
import com.aldreduser.gymroutine.ui.main.viewmodel.WorkoutListViewModel
import com.aldreduser.gymroutine.utils.FIRST_TAB_TITLE

private const val TAG = "WorkoutList__TAG"

class WorkoutListFragment : Fragment() {

    private var binding: FragmentWorkoutListBinding? = null
    private val viewModel: WorkoutListViewModel by activityViewModels()
    private lateinit var recyclerAdapter: WorkoutListAdapter
    private var groupToDisplay = FIRST_TAB_TITLE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentWorkoutListBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        recyclerAdapter = WorkoutListAdapter(viewModel, requireContext(), viewLifecycleOwner)
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            workoutListRecycler.adapter = recyclerAdapter
            workoutListRecycler.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
        setObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.currentGroup = groupToDisplay
        viewModel.toggleHiddenTxt()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setObservers() {
        viewModel.workouts.observe(viewLifecycleOwner) {
            // Only update the list when a workout is added or deleted.
            if (groupToDisplay == FIRST_TAB_TITLE) {
                if (recyclerAdapter.itemCount == 0) recyclerAdapter.submitList(it)
            } else {
                viewModel.getWorkoutsOfGroup(groupToDisplay)
                    .observe(viewLifecycleOwner) { groupedWorkouts ->
                        if (recyclerAdapter.itemCount == 0)
                            recyclerAdapter.submitList(groupedWorkouts)
                    }
            }
        }
    }

    companion object {
        fun getInstance(titleToDisplay: String): WorkoutListFragment {
            val thisFragment = WorkoutListFragment()
            thisFragment.groupToDisplay = titleToDisplay
            return thisFragment
        }
    }
}