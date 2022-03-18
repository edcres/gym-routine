package com.aldreduser.gymroutine.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aldreduser.gymroutine.R

// todo: Display the workouts in a custom position
//   have a position attribute for the workout entities

// todo: make the spinner pretty

// todo: maybe make it so the user can't delete the last set.

// todo: take care of todos
// todo: take care of warnings
// todo: clean up comments
// todo: clean up logs
// todo: clean up unused imports

// Future
// Ask the user before deleting an item
// Cap the groups at about 30
// When editing a new group, have the group name show up on the spinner
// Have a history of previous workouts
// Section for maxes and history of maxes
// Content descriptors
// Image to cancel edit mode (maybe contextual action bar)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
