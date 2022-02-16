package com.aldreduser.gymroutine.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.aldreduser.gymroutine.R
import com.aldreduser.gymroutine.data.WorkoutRepository
import com.aldreduser.gymroutine.data.model.room.WorkoutsRoomDatabase
import com.aldreduser.gymroutine.databinding.FragmentEditWorkoutBinding
import com.aldreduser.gymroutine.ui.main.viewmodels.WorkoutListViewModel
import com.aldreduser.gymroutine.ui.main.viewmodels.WorkoutListViewModelFactory

class EditWorkoutFragment : Fragment() {

    private var binding: FragmentEditWorkoutBinding? = null
    private lateinit var workoutListViewModel: WorkoutListViewModel
    private val simpleSpinnerItem = android.R.layout.simple_spinner_item

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentEditWorkoutBinding
            .inflate(inflater, container, false)
        binding = fragmentBinding
        return return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModel()
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            chooseCategorySpinner.setOnClickListener { spinnerOnClick() }
            editWorkoutDoneFab.setOnClickListener { doneFabOnClick() }
        }
        setUpAppBar()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    // CLICK LISTENERS //
    private fun spinnerOnClick() {

        // todo: change these items and make them the workout categories
        val categories = arrayOf("Choose Department", "Pro Desk", "Flooring",
            "Customer Service", "Appliances", "Millwork")
        binding?.chooseCategorySpinner?.adapter = ArrayAdapter<String>(this, simpleSpinnerItem, categories)
        binding?.chooseCategorySpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //textForSpinner.text = "Choose Department"
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                // choose what happens when each option is clicked (position)
//                when (options[position]) {
//                    //each one should be a string (ie. "Flooring")
//                    //options[0]  is the default text "Choose Department"    (quick fix)
//                    options[1] -> sendDepartmentName(options[1])
//                    options[2] -> sendDepartmentName(options[2])
//                    options[3] -> sendDepartmentName(options[3])
//                    options[4] -> sendDepartmentName(options[4])
//                    options[5] -> sendDepartmentName(options[5])
//                }
            }
        }
    }

    private fun doneFabOnClick() {
        // saves workout and goes back to the main screen
        // todo: handle fab click
    }

    // SETUP FUNCTIONS //
    private fun setUpAppBar() {
        // todo: put the name of the workout in the title
        binding?.editWorkoutTopAppbar?.title = "Name of workout"

        binding?.editWorkoutTopAppbar?.setNavigationOnClickListener {
            //todo: handle navigation icon press
        }
    }

    // SET UP FUNCTIONS //
    private fun setUpViewModel() {
        val application = requireActivity().application
        val database = WorkoutsRoomDatabase.getInstance(application)
        val repository = WorkoutRepository.getInstance(database)
        val viewModelFactory = WorkoutListViewModelFactory(repository, application)
        workoutListViewModel = ViewModelProvider(
            this, viewModelFactory).get(WorkoutListViewModel::class.java)
    }

    // HELPER FUNCTIONS //
}