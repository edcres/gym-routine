package com.aldreduser.gymroutine.ui.main.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.aldreduser.gymroutine.data.WorkoutRepository
import com.aldreduser.gymroutine.data.model.room.WorkoutsRoomDatabase
import com.aldreduser.gymroutine.databinding.ActivityEditWorkoutBinding
import com.aldreduser.gymroutine.ui.main.viewmodels.WorkoutsListViewModel
import com.aldreduser.gymroutine.ui.main.viewmodels.WorkoutsListViewModelFactory

class EditWorkoutActivity : AppCompatActivity() {

    private var binding: ActivityEditWorkoutBinding? = null
    private lateinit var workoutsListViewModel: WorkoutsListViewModel
    private val simpleSpinnerItem = android.R.layout.simple_spinner_item

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditWorkoutBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setUpViewModel()
        binding?.apply {
            lifecycleOwner = this@EditWorkoutActivity
            viewModel = workoutsListViewModel
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
        val application = requireNotNull(this).application
        val database = WorkoutsRoomDatabase.getInstance(application)
        val repository = WorkoutRepository.getInstance(database)
        val viewModelFactory = WorkoutsListViewModelFactory(repository, application)
        workoutsListViewModel = ViewModelProvider(
            this, viewModelFactory).get(WorkoutsListViewModel::class.java)
    }

    // HELPER FUNCTIONS //
}