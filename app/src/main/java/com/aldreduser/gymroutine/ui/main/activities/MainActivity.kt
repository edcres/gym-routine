package com.aldreduser.gymroutine.ui.main.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.aldreduser.gymroutine.R
import com.aldreduser.gymroutine.data.WorkoutRepository
import com.aldreduser.gymroutine.data.model.room.WorkoutsRoomDatabase
import com.aldreduser.gymroutine.databinding.ActivityMainBinding
import com.aldreduser.gymroutine.ui.main.viewmodels.WorkoutsListViewModel
import com.aldreduser.gymroutine.ui.main.viewmodels.WorkoutsListViewModelFactory
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

// Databinding (? observer vs flow vs liveData)
// todo: do databinding

// ui
// navigation tabs.
// todo: add navigation tabs in main activity (maybe make the selected one have the secondary color)
//  Tabs stay in screen when the user scrolls up, even tho the topAppBar disappears.
// todo: make fragments for the tabs
// todo: set up a viewpager2 to make the tabs dynamic (viewpager2 fragment)
//  (https://www.youtube.com/watch?v=nKkXNB5tvZc  (the view adapter class is in java, try to make it in kotlin)
//  if that video doesn't work    ->       tutorial for making viewpager2 tabs (no fragments)   https://www.youtube.com/watch?v=h41FnEH91D0  (have to watch the previous video)
//   -(use a fragment instead of the other layout file)
// todo: add tabs when user adds more categories

// recyclerview
// when recycler items are added, the FAB might scroll with the recyclerview, this didn't happen with a scrollview
// codelabs https://developer.android.com/codelabs/kotlin-android-training-recyclerview-fundamentals#0
// maybe use a GridLayoutManager instead of linear layout
// todo: have 2 columns
// todo: recyclerview displays workouts organized by categories

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

// todo: look up how to add categories programmatically to the tablayout

// Prettiness
// add more of the secondary color to the main activity

// in the future
// have a history of previous workouts
// section for maxes and history of maxes
// rn now, max number of sets is 6, make the max way higher
//  -each workout must have at least 1 set, other sets can be added or deleted ()
//      -maybe look into data tables in material.io
//      -each set is in a linear layout so that it can be added (as an xml item file) deleted
//      -make text inputs in material design
// content descriptors

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var workoutsListViewModel: WorkoutsListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViewModel()
        binding?.apply {
            lifecycleOwner = this@MainActivity
            viewModel = workoutsListViewModel       // todo: bug here
            // bug cause: ViewModel is expecting arguments (copy them from the other app, I forgot which one, maybe two-way-databinding)
            addWorkoutFab.setOnClickListener { fabOnClick() }
        }
        
        setUpAppBar()
        setUpTabLayout()
    }

    // handle fab click
    private fun fabOnClick() {
        // add workout
        // todo: handle click
    }

    private fun setUpViewModel() {
        val application = requireNotNull(this).application
        val database = WorkoutsRoomDatabase.getInstance(this)    // maybe not 'this', 'application' instead
        val repository = WorkoutRepository(database)  // todo: make this as a coroutine
        val viewModelFactory = WorkoutsListViewModelFactory(repository, application)   //todo : change this to work with coroutines
        workoutsListViewModel = ViewModelProvider(
                this, viewModelFactory).get(WorkoutsListViewModel::class.java)
    }

    private fun setUpAppBar() {
        binding.mainActivityTopAppbar.title = "Workouts"

        binding.mainActivityTopAppbar.setNavigationOnClickListener {
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