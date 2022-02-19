package com.aldreduser.gymroutine.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aldreduser.gymroutine.R

// todo: Room Database
// set up all the queries

// todo: navigation
// (make sure this is good) navigation and arrow icon in all activities (except the one
//      that opens when the app opens)

// todo: take care of todos
// todo: take care of warnings
// todo: clean up comments
// todo: clean up logs
// todo: clean up unused imports

// Future
// have a history of previous workouts
// section for maxes and history of maxes
// rn the max number of sets is 6, make the max way higher
//  -each workout must have at least 1 set, other sets can be added or deleted ()
//      -maybe look into data tables in material.io
//      -each set is in a linear layout so that it can be added (as an xml item file) deleted
//      -make text inputs in material design
//      -set up databinding for dynamic # of sets
//  -maybe have reusable layouts, databind it, and make it work with the remote and local repos
// content descriptors
// reuse the same fragment to add more tabs, maybe cap the maxNumOfTabs

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
