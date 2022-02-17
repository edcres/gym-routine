package com.aldreduser.gymroutine.ui.main.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import com.aldreduser.gymroutine.data.WorkoutsRepository
import com.aldreduser.gymroutine.data.model.entities.Workout
import com.aldreduser.gymroutine.data.model.entities.WorkoutGroup
import com.aldreduser.gymroutine.data.model.entities.WorkoutSet
import com.aldreduser.gymroutine.data.model.room.WorkoutsRoomDatabase
import com.aldreduser.gymroutine.ui.main.adapters.TabsViewPager2Adapter
import com.aldreduser.gymroutine.utils.FIRST_TAB_TITLE
import kotlinx.coroutines.launch

// The ViewModel will transform the data from the Repository,
//  from Flow to LiveData and exposes the list of words as LiveData to the UI.
class WorkoutListViewModel : ViewModel() {

    private lateinit var roomDb: WorkoutsRoomDatabase
    private lateinit var repository: WorkoutsRepository

    var groupToDisplay: String = FIRST_TAB_TITLE
    val groupsOrdinals: MutableMap<String, Int> = mutableMapOf(FIRST_TAB_TITLE to 0)

    private lateinit var activityViewPager2Adapter: TabsViewPager2Adapter
    // Get Complete Lists from Repo (these should happen when the viewmodel is called)

    // todo: merge 'groups' with 'workoutGroups' and make all of these observable liveData
    private val _groups = MutableLiveData<MutableList<WorkoutGroup>>()  // repository.allWorkoutGroups.asLiveData()
    val groups: LiveData<MutableList<WorkoutGroup>> get() = _groups
    private val _workouts = MutableLiveData<MutableList<Workout>>()  // repository.allWorkouts.asLiveData()
    val workouts: LiveData<MutableList<Workout>> get() = _workouts
    private val _sets = MutableLiveData<MutableList<WorkoutSet>>()  // repository.allWorkoutSets.asLiveData()
    val sets: LiveData<MutableList<WorkoutSet>> get() = _sets

    val groupNames: MutableList<String> = mutableListOf(FIRST_TAB_TITLE)
    private var tabWorkoutGroup = FIRST_TAB_TITLE


    // SETUP //
    fun startApplication(application: Application) {
        roomDb = WorkoutsRoomDatabase.getInstance(application)
        repository = WorkoutsRepository(roomDb)
    }
    // SETUP //


    // DATABASE QUERIES //
    // Workout Group //
    fun insertWorkoutGroup(workoutGroup: WorkoutGroup) = viewModelScope.launch {
        repository.insert(workoutGroup)
    }
    // Workout //
    fun insertWorkout(workout: Workout) = viewModelScope.launch {
        repository.insert(workout)
    }
    // Workout Set //
    fun insertWorkoutSet(workoutSet: WorkoutSet) = viewModelScope.launch {
        repository.insert(workoutSet)
    }
    // DATABASE QUERIES //

    // TABS //
    fun setViewPager2Adapter(context: Context) {
        // todo: possible bug: context might not turn into FragmentActivity
        activityViewPager2Adapter = TabsViewPager2Adapter(context as FragmentActivity)
    }
    fun getViewPager2Adapter(): TabsViewPager2Adapter {
        return activityViewPager2Adapter
    }
    private fun addTab(titleToAdd: String) {
        val nextTitlePosition = groupNames.size - 1
        val nextOrdinalId = groupsOrdinals.size - 1

        // todo: if 'tabTitles' contains 'nextTitle', don't add it, pop-up dialog to replace the title

        if(!groupNames.contains(titleToAdd)) {
            activityViewPager2Adapter.addTab(nextOrdinalId+1, titleToAdd)
        } else {
            Log.d("${MY_LOG}Activity", "\t\t titles contains next title " +
                    "\t\t \${$groupNames} $titleToAdd")
        }
    }
    private fun removeTab(titleToRemove: String) {
        val numOfTabs = groupNames.size
        if (numOfTabs > 1 && titleToRemove != FIRST_TAB_TITLE) {
            activityViewPager2Adapter.removeTab(titleToRemove)
        }
    }
    // TABS //
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