package com.aldreduser.gymroutine.ui.main.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.Navigation
import com.aldreduser.gymroutine.data.model.entities.Workout
import com.aldreduser.gymroutine.data.model.entities.WorkoutSet
import com.aldreduser.gymroutine.databinding.FragmentEditWorkoutBinding
import com.aldreduser.gymroutine.ui.main.adapters.SetsAdapter
import com.aldreduser.gymroutine.ui.main.viewmodel.WorkoutListViewModel

class EditWorkoutFragment : Fragment() {

    private val fragmentTAG = "EditFragmentTAG"
    private var binding: FragmentEditWorkoutBinding? = null
    private lateinit var viewModel: WorkoutListViewModel
    private lateinit var setsAdapter: SetsAdapter
    private lateinit var workoutName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentEditWorkoutBinding
            .inflate(inflater, container, false)
        binding = fragmentBinding
        setsAdapter = SetsAdapter(viewModel, true)
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            groupSpinner.setOnClickListener { spinnerOnClick() }
            addSetButton.setOnClickListener { addSetClick() }
            editWorkoutDoneFab.setOnClickListener { doneFabOnClick() }
            editSetListRecycler.adapter = setsAdapter
        }
        setUpAppBar()
        workoutName = viewModel.currentWorkoutName!!
        setsAdapter.submitList(viewModel.getSetsOfThisWorkout(workoutName))
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    // CLICK LISTENERS //
    private fun spinnerOnClick() {
        val simpleSpinnerItem = android.R.layout.simple_spinner_item
        binding?.apply {
            groupSpinner.adapter =
                ArrayAdapter(requireContext(), simpleSpinnerItem, viewModel.groupNames)
            groupSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val groupSelected = viewModel.groupNames[position]
                    viewModel.addGroupToWorkout(
                        Workout(viewModel.currentWorkoutName!!, groupSelected)
                    )
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Log.i(fragmentTAG, "Nothing selected.")
                }
            }
        }
    }
    private fun addSetClick() {
        // todo: maybe have to wait for the result of 'nextSet' to insert workout
        val nextSet = viewModel.getNextSetNum(workoutName)
        val workoutPlusSet = "$workoutName$nextSet"
        viewModel.insertWorkoutSet(WorkoutSet(
            workoutPlusSet,
            workoutName,
            nextSet,
            0,
            0.0
        ))
        // todo: maybe have to wait for the result of the insert query to submitList()
        setsAdapter.submitList(viewModel.getSetsOfThisWorkout(workoutName))
    }
    private fun doneFabOnClick() {
        val navController = Navigation.findNavController(requireParentFragment().requireView())
        navController.navigateUp()
    }
    // CLICK LISTENERS //

    // SETUP FUNCTIONS //
    private fun setUpAppBar() {
        binding?.apply {
            editWorkoutTopAppbar.title = viewModel.currentWorkoutName
            editWorkoutTopAppbar.setNavigationOnClickListener {
                val navController =
                    Navigation.findNavController(requireParentFragment().requireView())
                navController.navigateUp()
            }
        }
    }
}