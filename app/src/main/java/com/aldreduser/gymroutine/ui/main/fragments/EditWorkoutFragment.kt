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
import com.aldreduser.gymroutine.R
import com.aldreduser.gymroutine.data.model.entities.WorkoutGroup
import com.aldreduser.gymroutine.data.model.entities.WorkoutSet
import com.aldreduser.gymroutine.databinding.FragmentEditWorkoutBinding
import com.aldreduser.gymroutine.ui.main.adapters.SetsAdapter
import com.aldreduser.gymroutine.ui.main.viewmodel.WorkoutListViewModel
import com.aldreduser.gymroutine.utils.getChooseGroupList

private const val TAG = "EditFrag__TAG"

class EditWorkoutFragment : Fragment() {

    private var binding: FragmentEditWorkoutBinding? = null
    private val vm: WorkoutListViewModel by activityViewModels()
    private lateinit var setsAdapter: SetsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentEditWorkoutBinding
            .inflate(inflater, container, false)
        binding = fragmentBinding
        setsAdapter = SetsAdapter(vm, true)
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
        setUpAppBar()
        submitsSetsOfWorkout()
        vm.sets.observe(viewLifecycleOwner) {
            if (vm.editWorkoutSetsPreviousSize != it.size) {
                submitsSetsOfWorkout()
                vm.editWorkoutSetsPreviousSize = it.size
            }
        }
        vm.turnOffEditMode()
        setObservers()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        vm.editWorkoutSetsPreviousSize = 0
    }

    // HELPERS //
    private fun submitsSetsOfWorkout() {
        vm.getSetsOfWorkout(vm.workoutIdToEdit!!).observe(viewLifecycleOwner) { sets ->
            setsAdapter.submitList(sets)
        }
    }

    private fun saveMusclesAndNotes() {
        binding!!.apply {
            vm.updateWorkoutNotes(
                vm.workoutIdToEdit ?: vm.workoutIdToEdit!!,
                muscleTargetedEt.text.toString(),
                workoutNotesEt.text.toString()
            )
        }
    }
    // HELPERS //

    // CLICK LISTENERS //
    private fun spinnerOnClick() {
        binding?.apply {
            groupSpinner.adapter = ArrayAdapter(
                requireContext(),
                R.layout.groups_spinner_item,
                getChooseGroupList(vm.groupNames)
            )
            groupSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (vm.groupNames.size == position - 1) {
                        // If user clicks new group.
                        groupSpinner.visibility = View.GONE
                        groupEtContainer.visibility = View.VISIBLE
                        newGroupDoneBtn.setOnClickListener {
                            vm.insertWorkoutGroup(
                                WorkoutGroup(newGroupEt.text.toString()),
                                vm.workoutIdToEdit!!
                            )
                            groupEtContainer.visibility = View.GONE
                        }
                    } else if (position > 0 && position - 1 < vm.groupNames.size) {
                        // If it's not the first item and not the last one.
                        val groupSelected = vm.groupNames[position - 1]
                        vm.updateGroupOnWorkout(vm.workoutIdToEdit!!, groupSelected)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Log.i(TAG, "Nothing selected.")
                }
            }
        }
    }

    private fun addSetClick() {
        vm.getLastSet(vm.workoutIdToEdit!!).observe(viewLifecycleOwner) { lastSet ->
            vm.insertWorkoutSet(
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
        saveMusclesAndNotes()
        val navController = Navigation.findNavController(requireParentFragment().requireView())
        navController.navigateUp()
    }
    // CLICK LISTENERS //

    // SETUP FUNCTIONS //
    private fun setUpAppBar() {
        binding?.apply {
            vm.getWorkoutName(vm.workoutIdToEdit!!).observe(viewLifecycleOwner) {
                editWorkoutTopAppbar.title = it
            }
            editWorkoutTopAppbar.setNavigationOnClickListener {
                val navController =
                    Navigation.findNavController(requireParentFragment().requireView())
                navController.navigateUp()
            }
        }
    }

    private fun setObservers() {
        vm.getWorkoutWithId(vm.workoutIdToEdit ?: vm.workoutIdToEdit!!)
            .observe(viewLifecycleOwner) {
                binding?.apply {
                    muscleTargetedEt.setText(it.musclesTargeted)
                    workoutNotesEt.setText(it.notes)
                }
            }
    }
    // SETUP FUNCTIONS //
}