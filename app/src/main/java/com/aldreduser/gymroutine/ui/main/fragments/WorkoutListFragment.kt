package com.aldreduser.gymroutine.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.aldreduser.gymroutine.databinding.FragmentWorkoutListBinding
import com.aldreduser.gymroutine.ui.main.adapters.WorkoutListAdapter
import com.aldreduser.gymroutine.ui.main.viewmodel.WorkoutListViewModel

class WorkoutListFragment : Fragment() {

    private val fragmentTAG = "Workout List FragmentTAG"
    private var binding: FragmentWorkoutListBinding? = null
    private val viewModel: WorkoutListViewModel by activityViewModels()
    private lateinit var recyclerAdapter: WorkoutListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentWorkoutListBinding.inflate(inflater ,container, false)
        binding = fragmentBinding
        recyclerAdapter = WorkoutListAdapter(viewModel, viewLifecycleOwner)
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            workoutListRecycler.adapter = recyclerAdapter
            workoutListRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
