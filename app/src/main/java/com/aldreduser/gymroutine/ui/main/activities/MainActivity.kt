package com.aldreduser.gymroutine.ui.main.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.aldreduser.gymroutine.data.WorkoutRepository
import com.aldreduser.gymroutine.data.model.room.WorkoutsRoomDatabase
import com.aldreduser.gymroutine.databinding.ActivityMainBinding
import com.aldreduser.gymroutine.ui.main.adapters.TabsViewPager2Adapter
import com.aldreduser.gymroutine.ui.main.viewmodels.WorkoutsListViewModel
import com.aldreduser.gymroutine.ui.main.viewmodels.WorkoutsListViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

// todo: ViewPager2
// do: adapter, fragment, activity
// add/remove tabs from 'EditWorkoutFragment'

//todo: get rid of allWorkouts fragment
//todo: get rid of 'MainViewPager2Adapter'
//todo: get rid of 'AllWorkoutsListAdapter'

// todo: edit viewModel and fragment views connected to it
// - each viewModel variable will correspond to each view widget (make more variables)
// - make livedata lists. Hold sets, reps, and weight (maybe look at old app for reference)

// todo: Room Database
// learn about databases multiple entities with relations

// todo: set up all the queries

// todo: every time the activity is created, populate 'tabTitles' and 'titlesOrdinals' (located in the viewModel) with each workout group
// todo: when the app is started, if 'allWorkoutGroups' in the database is empty: add 'All Workouts'
// todo: when all the workouts in a workout group are removed, remove the group and the tab

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
// reuse the same fragment to add more tabs, maybe cap the maxNumOfTabs

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
        setUpTabs()
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

    // SETUP FUNCTIONS //
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

    private fun setUpTabs() {
        workoutsListViewModel.setViewPager2Adapter(this)
        binding!!.workoutsListViewPager2.adapter = workoutsListViewModel.getViewPager2Adapter()

        TabLayoutMediator(binding!!.mainTabLayout, binding!!.workoutsListViewPager2) { tab, position ->
            tab.text = workoutsListViewModel.tabTitles[position]
        }.attach()
    }

    // HELPER FUNCTIONS //
    fun getViewModel(): WorkoutsListViewModel {
        return workoutsListViewModel
    }
}
