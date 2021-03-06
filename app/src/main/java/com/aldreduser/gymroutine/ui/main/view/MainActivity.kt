package com.aldreduser.gymroutine.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aldreduser.gymroutine.R
import kotlinx.android.synthetic.main.activity_main.*

// room codelabs https://developer.android.com/codelabs/android-room-with-a-view-kotlin/#3

// ui
//edit workout activity
// todo: activity widgets
//add recyclerview item
//navigation tabs
// todo: add navigation tabs in main activity

// database (room)
// todo: have the workouts organized in different categories (ie chest, arms, legs)
//  -user can add, delete, edit categories. Can change the names
//Entity
// todo: workout group
// todo: workout
// todo: set
//DAO
//Database
//Repo
//ViewModel

// recyclerview
// todo: have 2 columns
// todo: recyclerview displays workouts organized by categories

// Databinding

// future
// have a history of previous workouts
// section for maxes and history of maxes
// rn now, max number of sets is 6, make the max way higher
//  -(maybe have a layout fragment with delete button, set number, reps for the set and weight in a linear layout)
//  -maybe look into datatables in material.io

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpAppBar()

        fabOnClick()
    }

    private fun fabOnClick() {
        // add workout
        addWorkoutFab.setOnClickListener {
            // todo: handle click
        }
    }

    private fun setUpAppBar() {
        mainActivityTopAppBar.title = "Workouts"

        mainActivityTopAppBar.setNavigationOnClickListener {
            // todo: handle navigation icon press
        }
    }

}