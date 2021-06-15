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

// todo: ViewPager2
// set up a viewpager2 to make the tabs dynamic (viewpager2 fragment)
//  (https://www.youtube.com/watch?v=nKkXNB5tvZc  (the view adapter class is in java, try to make it in kotlin)
//  if that video doesn't work    ->       tutorial for making viewpager2 tabs (no fragments)   https://www.youtube.com/watch?v=h41FnEH91D0  (have to watch the previous video)
// add tabs when user adds more categories

// todo: EditWorkoutFragment should probably be an activity.
// todo: There should probably only be one fragment that holds the list (just organized differently)

// todo: Room Database
// learn about databases multiple entities with relations

// todo: set up all the queries

// todo: recyclerview
// DataBinding in the recycler items might be wrong.
// Codelabs https://developer.android.com/codelabs/kotlin-android-training-recyclerview-fundamentals#0
// Display workouts by categories (or all) in the recyclerViews.
// add workout to recyclerview and refresh list

// todo: navigation (edit)
// (make sure this is good) navigation and arrow icon in all activities (except the one that opens when the app opens)
// when user backs out of adding a new item, ask if they're sure they wanna cancel.
// when user goes back in navigation from 'add shoppingList item activity', app asks to cancel adding new activity

// todo: clean up unused imports
// todo: take care of warnings
// todo: clean up comments

// Prettiness
// add more of the secondary color to the main activity

// in the future
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

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private lateinit var workoutsListViewModel: WorkoutsListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setUpViewModel()
        binding?.apply {
            lifecycleOwner = this@MainActivity
            viewModel = workoutsListViewModel
            // bug cause: ViewModel is expecting arguments (copy them from the other app, I forgot which one, maybe two-way-databinding)
            addWorkoutFab.setOnClickListener { fabOnClick() }
        }
        
        setUpAppBar()
        setUpTabLayout()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    // CLICK HANDLERS //
    private fun fabOnClick() {
        // add workout
        // todo: handle click
    }

    private fun setUpViewModel() {
        val application = requireNotNull(this).application
        val database = WorkoutsRoomDatabase.getInstance(this)    // maybe not 'this', 'application' instead
        val repository = WorkoutRepository.getInstance(database)
        val viewModelFactory = WorkoutsListViewModelFactory(repository, application)
        workoutsListViewModel = ViewModelProvider(
                this, viewModelFactory).get(WorkoutsListViewModel::class.java)
    }

    private fun setUpAppBar() {
        binding?.mainActivityTopAppbar?.title = "Workouts"

        binding?.mainActivityTopAppbar?.setNavigationOnClickListener {
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
