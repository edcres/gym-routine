package com.aldreduser.gymroutine.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aldreduser.gymroutine.R

// todo: Room Database
// set up all the queries

// todo: Image to cancel edit mode

/*
todo:
 Update the workout name as the user types it
 - update Workout and WorkoutSet

- possible bug: when a user is typing a new workout and it is of the same name of a previous
    workout, it might override the other workout. Solution: onConflict:duplicate
	- duplicate probably wouldn't work bc name has to be unique
	- solution: send workoutName to the db when user clicks accept
 */

// -each workout must have at least 1 set, other sets can be added or deleted ()

// todo: take care of todos
// todo: take care of warnings
// todo: clean up comments
// todo: clean up logs
// todo: clean up unused imports

// Future
// Have a history of previous workouts
// Section for maxes and history of maxes
// Content descriptors
// Cap the groups at about 30

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
