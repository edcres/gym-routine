package com.aldreduser.gymroutine.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.aldreduser.gymroutine.R

// - todo: major bug:
//      when updating reps and weight in a workout in workouts list fragment. all reps and weight
//      in that workout are updated to the same in that set. This works fine in edit screen.
//      Maybe it's bc i have the custom list adapter in the edit fragment when i edit the first
//      set weight and add another set and edit that weight, the first set weight is set
//      to the weight of the new set.

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
// add a workout note in the edit screen (user can write notes)
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
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}
