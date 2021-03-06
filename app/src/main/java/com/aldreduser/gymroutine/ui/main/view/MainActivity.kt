package com.aldreduser.gymroutine.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aldreduser.gymroutine.R
import kotlinx.android.synthetic.main.activity_main.*

// room codelabs https://developer.android.com/codelabs/android-room-with-a-view-kotlin/#3

// ui
// todo: have a toolbar, but it hides when u scroll down

// database (room)
// todo: have the workouts organized in different categories (ie chest, arms, legs)
//  -user can add, delete, edit categories. Can change the names

// recyclerview
// todo: have 2 columns
// todo: recyclerview displays workouts organized by categories

// future
// have a history of previous workouts
// section for maxes and history of maxes

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpAppBar()
    }

    private fun setUpAppBar() {
        mainActivityTopAppBar.title = "Workouts"

        mainActivityTopAppBar.setNavigationOnClickListener {
            // todo: handle navigation icon press
        }
    }

}