package com.aldreduser.gymroutine.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.aldreduser.gymroutine.R
import com.aldreduser.gymroutine.ui.main.activities.MainActivity
import kotlinx.android.synthetic.main.fragment_edit_workout.*

class EditWorkoutFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_workout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpAppBar()

        spinnerOnClick()
        doneFabOnClick()
    }

    private fun spinnerOnClick() {
        // todo: change these items and make the the workout categories
        val categories = arrayOf("Choose Department", "Pro Desk", "Flooring",
            "Customer Service", "Appliances", "Millwork")
        chooseCategorySpinner.adapter = ArrayAdapter<String>(requireContext() ,android.R.layout.simple_spinner_item,categories)
        chooseCategorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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
        editWorkoutDoneFab.setOnClickListener {
            // todo: handle fab click
        }
    }

    private fun setUpAppBar() {
        // todo: put the name of the workout in the title
        editWorkoutTopAppBar.title = "Name of workout"

        editWorkoutTopAppBar.setNavigationOnClickListener {
            //todo: handle navigation icon press
        }
    }
}

