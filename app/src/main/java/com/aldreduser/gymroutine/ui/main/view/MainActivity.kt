package com.aldreduser.gymroutine.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aldreduser.gymroutine.R
import kotlinx.android.synthetic.main.activity_main.*

// room codelabs https://developer.android.com/codelabs/android-room-with-a-view-kotlin/#3

// ui
// todo: add button in the main activity
// todo: add workout activity
//toolbar
// todo: have a toolbar, but it hides when u scroll down
// todo: toolbar in add workout activity
// todo: toolbar in add workout activity
// navigation tabs
// todo: add navigation tabs in main activity

// database (room)
// todo: have the workouts organized in different categories (ie chest, arms, legs)
//  -user can add, delete, edit categories. Can change the names
//Entity
// todo: workout goup
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

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpAppBar()

        fabOnClick()
    }

    private fun fabOnClick() {
        // todo: pass some data to that activity
        // navigate to add workout activity
        addWorkoutFab.setOnClickListener {
            val newIntent = Intent(this, AddShoppingItemActivity::class.java)
            startActivity(newIntent)
        }
    }

    private fun setUpAppBar() {
        mainActivityTopAppBar.title = "Workouts"

        mainActivityTopAppBar.setNavigationOnClickListener {
            // todo: handle navigation icon press
        }
    }

}