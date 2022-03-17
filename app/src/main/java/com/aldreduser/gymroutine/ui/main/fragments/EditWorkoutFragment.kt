package com.aldreduser.gymroutine.ui.main.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.aldreduser.gymroutine.data.model.entities.WorkoutGroup
import com.aldreduser.gymroutine.data.model.entities.WorkoutSet
import com.aldreduser.gymroutine.databinding.FragmentEditWorkoutBinding
import com.aldreduser.gymroutine.ui.main.adapters.SetsAdapter
import com.aldreduser.gymroutine.ui.main.viewmodel.WorkoutListViewModel
import com.aldreduser.gymroutine.utils.getChooseGroupList

class EditWorkoutFragment : Fragment() {

    private val fragmentTAG = "EditFrag_TAG"
    private var binding: FragmentEditWorkoutBinding? = null
    private val viewModel: WorkoutListViewModel by activityViewModels()
    private lateinit var setsAdapter: SetsAdapter
    private var currentWorkoutId: Long? = null
    private var setsPreviousSize: Int = 0

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
            spinnerOnClick()
            addSetButton.setOnClickListener { addSetClick() }
            editWorkoutDoneFab.setOnClickListener { doneFabOnClick() }
            editSetListRecycler.adapter = setsAdapter
            editSetListRecycler.layoutManager = LinearLayoutManager(requireContext())
        }
        currentWorkoutId = viewModel.workoutIdToEdit
        setUpAppBar()
        submitsSetsOfWorkout()
        viewModel.sets.observe(viewLifecycleOwner) {
            // Observe when a set is added or removed.

            Log.d(fragmentTAG, "setsPreviousSize = $setsPreviousSize --- nowSize = ${it.size}")
            if(setsPreviousSize != it.size) {
                submitsSetsOfWorkout()
                setsPreviousSize = it.size
            }
        }
        viewModel.toggleEditBtn()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    // HELPERS //
    private fun submitsSetsOfWorkout() {
        viewModel.getSetsOfWorkout(currentWorkoutId!!).observe(viewLifecycleOwner) { sets ->
//             todo: i don't think this does anything
            val submitsSetsOfWorkout = mutableListOf<String>()
            sets.forEach {
                submitsSetsOfWorkout.add("${it.id}\t${it.set}\n")
            }
            Log.d(fragmentTAG, "submitsSetsOfWorkout: \n$submitsSetsOfWorkout")

            setsAdapter.submitList(sets)
        }
    }

    // CLICK LISTENERS //
    private fun spinnerOnClick() {
        binding?.apply {
            groupSpinner.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                getChooseGroupList(viewModel.groupNames)
            )
            groupSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (viewModel.groupNames.size == position-1) {
                        // If user clicks new group
                        groupSpinner.visibility = View.GONE
                        groupEtContainer.visibility = View.VISIBLE
                        newGroupDoneBtn.setOnClickListener {
                            viewModel.insertWorkoutGroup(
                                WorkoutGroup(newGroupEt.text.toString()),
                                currentWorkoutId!!
                            )
                            groupSpinner.visibility = View.VISIBLE
                            groupEtContainer.visibility = View.GONE
                        }
                    } else if (position > 0 && position-1 < viewModel.groupNames.size) {
                        // If it's not the first item and not the last one
                        val groupSelected = viewModel.groupNames[position-1]
                        viewModel.updateGroupOnWorkout(currentWorkoutId!!, groupSelected)
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Log.i(fragmentTAG, "Nothing selected.")
                }
            }
        }
    }
    private fun addSetClick() {
        viewModel.getLastSet(currentWorkoutId!!).observe(viewLifecycleOwner) { lastSet ->
            viewModel.insertWorkoutSet(
                WorkoutSet(
                    workoutId = lastSet.workoutId,
                    workoutName = lastSet.workoutName,
                    set = lastSet.set + 1,
                    reps = 0,
                    weight = 0.0
                )
            )
        }
    }
    private fun doneFabOnClick() {
        val navController = Navigation.findNavController(requireParentFragment().requireView())
        navController.navigateUp()
    }
    // CLICK LISTENERS //

    // SETUP FUNCTIONS //
    private fun setUpAppBar() {
        binding?.apply {
            viewModel.getWorkoutName(currentWorkoutId!!).observe(viewLifecycleOwner) {
                editWorkoutTopAppbar.title = it
            }
            editWorkoutTopAppbar.setNavigationOnClickListener {
                val navController =
                    Navigation.findNavController(requireParentFragment().requireView())
                navController.navigateUp()
            }
        }
    }
}