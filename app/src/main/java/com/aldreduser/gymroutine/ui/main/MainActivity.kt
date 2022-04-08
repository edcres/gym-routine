package com.aldreduser.gymroutine.ui.main

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.aldreduser.gymroutine.R

// todo: Display the workouts in a custom position
//   have a position attribute for the workout entities

// todo: make the spinner pretty
//      - Maybe look at the drop down menu in the develop with freedom app.

// todo: maybe make it so the user can't delete the last set.

// todo: take care of todos
// todo: take care of warnings
// todo: clean up comments
// todo: clean up logs
// todo: clean up unused imports
// todo: indent code correctly

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.black)
        }
    }
}
