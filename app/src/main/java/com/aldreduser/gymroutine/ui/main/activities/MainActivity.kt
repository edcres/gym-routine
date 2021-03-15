package com.aldreduser.gymroutine.ui.main.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aldreduser.gymroutine.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

// room codelabs https://developer.android.com/codelabs/android-room-with-a-view-kotlin/#3

// ui
// todo: make fragment: workouts list
//navigation tabs.
// todo: add navigation tabs in main activity (maybe make the selected one have the secondary color)
//  Tabs stay in screen when the user scrolls up, even tho the topAppBar disappears.
// todo: make fragments for the tabs
// todo: set up a viewpager2 to make the tabs dynamic (viewpager2 fragment)
//  (doesn't use fragment state adapters) https://www.youtube.com/watch?v=nKkXNB5tvZc  (the view adapter class is in java, try to make it in kotlin)
//  if that video doesn't work    ->       tutorial for making viewpager2 tabs (no fragments)   https://www.youtube.com/watch?v=h41FnEH91D0  (have to watch the previous video)
//   -(use a fragment instead of the other layout file)
// todo: add tabs when user adds more categories

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
// when recycler items are added, the FAB might scroll with the recyclerview, this didn't happen with a scrollview
// codelabs https://developer.android.com/codelabs/kotlin-android-training-recyclerview-fundamentals#0
// todo: have 2 columns
// todo: recyclerview displays workouts organized by categories

// Databinding
// databinding codelab: https://developer.android.com/codelabs/android-databinding#0

// todo: look up how to add categories programmatically to the tablayout

// Prettiness
// add more of the secondary color to the main activity

// in the future
// have a history of previous workouts
// section for maxes and history of maxes
// rn now, max number of sets is 6, make the max way higher
//  -(maybe have a layout fragment with delete button, set number, reps for the set and weight in a linear layout)
//  -maybe look into data tables in material.io

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpAppBar()
        setUpTabLayout()

        tabCategorySelected()
        fabOnClick()
    }

    // handle tab selection
    private fun tabCategorySelected() {
        mainTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                // Handle tab select
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselect
            }
        })
    }

    // handle fab click
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

    private fun setUpTabLayout() {


        /* how to customize specific tabs programmatically
        val tab = tabLayout.getTabAt(index)
        tab?.icon = drawable
         */
    }
}