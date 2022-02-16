package com.aldreduser.gymroutine.ui.main.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import com.aldreduser.gymroutine.data.WorkoutRepository
import com.aldreduser.gymroutine.data.model.entities.Workout
import com.aldreduser.gymroutine.data.model.entities.WorkoutGroup
import com.aldreduser.gymroutine.data.model.entities.WorkoutSet
import com.aldreduser.gymroutine.ui.main.activities.MainActivity
import com.aldreduser.gymroutine.ui.main.adapters.TabsViewPager2Adapter
import com.aldreduser.gymroutine.ui.main.fragments.WorkoutsGroupListFragment
import com.aldreduser.gymroutine.utils.FIRST_TAB_TITLE
import com.aldreduser.gymroutine.utils.MY_LOG
import kotlinx.coroutines.launch

// todo: use 'viewModelScope.launch' when retrieving data from repository

// The ViewModel will transform the data from the Repository,
//  from Flow to LiveData and exposes the list of words as LiveData to the UI.
class WorkoutListViewModel(
        private val repository: WorkoutRepository,
        private val application: Application) : ViewModel() {

    // Tab variables
    private var currentCategoryToDisplay: String = FIRST_TAB_TITLE
    private var currentWorkoutToEdit: String = FIRST_TAB_TITLE
    val tabTitles: MutableList<String> = mutableListOf(FIRST_TAB_TITLE)
    val tabTitlesOrdinals: MutableMap<String, Int> = mutableMapOf(FIRST_TAB_TITLE to 0)
    private var activityViewPager2Adapter: TabsViewPager2Adapter? = null

    // Get Complete Lists from Repo (these should happen when the viewmodel is called)
    val allWorkoutGroups: LiveData<List<WorkoutGroup>> = repository.allWorkoutGroups.asLiveData()
    val allWorkouts: LiveData<List<Workout>> = repository.allWorkouts.asLiveData()
    val allWorkoutSets: LiveData<List<WorkoutSet>> = repository.allWorkoutSets.asLiveData()

    // DATABASE QUERIES //
    // Workout Group //
    // insert
    fun insertWorkoutGroup(workoutGroup: WorkoutGroup) = viewModelScope.launch {
        repository.insert(workoutGroup)
    }
    // Workout //
    // insert
    fun insertWorkout(workout: Workout) = viewModelScope.launch {
        repository.insert(workout)
    }
    // Workout Set //
    // insert
    fun insertWorkoutSet(workoutSet: WorkoutSet) = viewModelScope.launch {
        repository.insert(workoutSet)
    }

    // MANAGE TABS //
    private fun addTab(titleToAdd: String) {
        val nextTitlePosition = tabTitles.size - 1
        val nextOrdinalId = tabTitlesOrdinals.size - 1

        // todo: if 'tabTitles' contains 'nextTitle', don't add it, pop-up dialog to replace the title

        if(!tabTitles.contains(titleToAdd)) {
            activityViewPager2Adapter!!.addTab(nextOrdinalId+1, titleToAdd)
        } else {
            Log.d("${MY_LOG}Activity", "\t\t titles contains next title " +
                    "\t\t \${$tabTitles} $titleToAdd")
        }
    }
    private fun removeTab(titleToRemove: String) {
        val numOfTabs = tabTitles.size
        if (numOfTabs > 1 && titleToRemove != FIRST_TAB_TITLE) {
            activityViewPager2Adapter!!.removeTab(titleToRemove)
        }
    }

    // HELPER FUNCTIONS //
    fun setViewPager2Adapter(context: Context) {
        // todo: possible bug: context might not turn into FragmentActivity
        activityViewPager2Adapter = TabsViewPager2Adapter(context as FragmentActivity)
    }
    fun getViewPager2Adapter(): TabsViewPager2Adapter {
        return activityViewPager2Adapter!!
    }
}




//    //this was supposed to store all the workout reps from all the workouts, but i don't think i need it anymore
//    val viewModelWorkoutReps: LiveData<MutableList<MutableList<Int>>> =
//        MutableLiveData<MutableList<MutableList<Int>>>()

// probably get rid of these
// These are commented out bc I will use lists instead, and maybe not connect them to livedata
//    // In the future, user can add maybe infinite sets and these variables will be lists: sets, reps, weight
//    private val _set1 = MutableLiveData<String>(); val set1: MutableLiveData<String> = _set1
//    private val _set2 = MutableLiveData<String>(); val set2: MutableLiveData<String> = _set2
//    private val _set3 = MutableLiveData<String>(); val set3: MutableLiveData<String> = _set3
//    private val _set4 = MutableLiveData<String>(); val set4: MutableLiveData<String> = _set4
//    private val _set5 = MutableLiveData<String>(); val set5: MutableLiveData<String> = _set5
//    private val _set6 = MutableLiveData<String>(); val set6: MutableLiveData<String> = _set6
//    // Reps
//    private val _set1Reps = MutableLiveData<String>(); val set1Reps: MutableLiveData<String> = _set1Reps
//    private val _set2Reps = MutableLiveData<String>(); val set2Reps: MutableLiveData<String> = _set2Reps
//    private val _set3Reps = MutableLiveData<String>(); val set3Reps: MutableLiveData<String> = _set3Reps
//    private val _set4Reps = MutableLiveData<String>(); val set4Reps: MutableLiveData<String> = _set4Reps
//    private val _set5Reps = MutableLiveData<String>(); val set5Reps: MutableLiveData<String> = _set5Reps
//    private val _set6Reps = MutableLiveData<String>(); val set6Reps: MutableLiveData<String> = _set6Reps
//    // Weight
//    private val _set1Weight = MutableLiveData<String>(); val set1Weight: MutableLiveData<String> = _set1Weight
//    private val _set2Weight = MutableLiveData<String>(); val set2Weight: MutableLiveData<String> = _set2Weight
//    private val _set3Weight = MutableLiveData<String>(); val set3Weight: MutableLiveData<String> = _set3Weight
//    private val _set4Weight = MutableLiveData<String>(); val set4Weight: MutableLiveData<String> = _set4Weight
//    private val _set5Weight = MutableLiveData<String>(); val set5Weight: MutableLiveData<String> = _set5Weight
//    private val _set6Weight = MutableLiveData<String>(); val set6Weight: MutableLiveData<String> = _set6Weight


//probably get rid of this, will be replaced by binding utils
//    // return the position of the current workout in the 'allWorkouts'
//    fun getCurrentWorkoutPosition(currentWorkoutName: String): Int {
//        var workoutPosition = 0
//        // add each workout name to a list of workout names
//        allWorkouts.value!!.forEach { v ->
//            if (currentWorkoutName == v.thisWorkoutName) {
//                return workoutPosition
//            }
//            workoutPosition ++
//        }
//        return workoutPosition
//    }