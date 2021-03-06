package com.aldreduser.gymroutine.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aldreduser.gymroutine.R
import kotlinx.android.synthetic.main.activity_add_workout.*

class EditWorkoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_workout)

        setUpAppBar()
    }

    private fun setUpAppBar() {
        editWorkoutTopAppBar.title = "Edit Workout"

        editWorkoutTopAppBar.setNavigationOnClickListener {
            //todo: handle navigation icon press
        }
    }

    private fun doneFabOnClick() {
        // saves workout and goes back to the main screen
        editWorkoutDoneFab.setOnClickListener {
            // todo: handle fab click
        }
    }
}