package com.aldreduser.gymroutine.ui.main.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.aldreduser.gymroutine.data.WorkoutRepository
import com.aldreduser.gymroutine.data.model.entities.Workout
import com.aldreduser.gymroutine.data.model.entities.WorkoutGroup
import com.aldreduser.gymroutine.data.model.entities.WorkoutSet
import com.aldreduser.gymroutine.utils.FIRST_TAB_TITLE
import kotlinx.coroutines.launch

// todo: use 'viewModelScope.launch' when retrieving data from repository

// The ViewModel will transform the data from the Repository,
//  from Flow to LiveData and exposes the list of words as LiveData to the UI.
class WorkoutsListViewModel(
        private val repository: WorkoutRepository,
        private val application: Application) : ViewModel() {

    // ViewPager2 variables
    // Complete Lists
    val allWorkoutGroups: LiveData<List<WorkoutGroup>> = repository.allWorkoutGroups.asLiveData()
    val allWorkouts: LiveData<List<Workout>> = repository.allWorkouts.asLiveData()
    val allWorkoutSets: LiveData<List<WorkoutSet>> = repository.allWorkoutSets.asLiveData()

    private val tabTitles: MutableList<String> = mutableListOf(FIRST_TAB_TITLE)
    private val tabTitlesOrdinals: MutableMap<String, Int> = mutableMapOf(FIRST_TAB_TITLE to 0)

    // DataBound Variables ()
    // In the future, user can add maybe infinite sets and these variables will be lists: sets, reps, weight
    private val _set1 = MutableLiveData<String>()
    val set1: MutableLiveData<String> = _set1
    private val _set2 = MutableLiveData<String>()
    val set2: MutableLiveData<String> = _set2
    private val _set3 = MutableLiveData<String>()
    val set3: MutableLiveData<String> = _set3
    private val _set4 = MutableLiveData<String>()
    val set4: MutableLiveData<String> = _set4
    private val _set5 = MutableLiveData<String>()
    val set5: MutableLiveData<String> = _set5
    private val _set6 = MutableLiveData<String>()
    val set6: MutableLiveData<String> = _set6
    // Reps
    private val _set1Reps = MutableLiveData<String>()
    val set1Reps: MutableLiveData<String> = _set1Reps
    private val _set2Reps = MutableLiveData<String>()
    val set2Reps: MutableLiveData<String> = _set2Reps
    private val _set3Reps = MutableLiveData<String>()
    val set3Reps: MutableLiveData<String> = _set3Reps
    private val _set4Reps = MutableLiveData<String>()
    val set4Reps: MutableLiveData<String> = _set4Reps
    private val _set5Reps = MutableLiveData<String>()
    val set5Reps: MutableLiveData<String> = _set5Reps
    private val _set6Reps = MutableLiveData<String>()
    val set6Reps: MutableLiveData<String> = _set5Reps
    // Weight
    private val _set1Weight = MutableLiveData<String>()
    val set1Weight: MutableLiveData<String> = _set1Weight
    private val _set2Weight = MutableLiveData<String>()
    val set2Weight: MutableLiveData<String> = _set2Weight
    private val _set3Weight = MutableLiveData<String>()
    val set3Weight: MutableLiveData<String> = _set3Weight
    private val _set4Weight = MutableLiveData<String>()
    val set4Weight: MutableLiveData<String> = _set4Weight
    private val _set5Weight = MutableLiveData<String>()
    val set5Weight: MutableLiveData<String> = _set5Weight
    private val _set6Weight = MutableLiveData<String>()
    val set6Weight: MutableLiveData<String> = _set6Weight

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
}

// get data from database concurrently
// ( from '3(RecyclerViewClickHandler)_SleepQuality' in the 'SleepTrackerViewModel' )
//private fun initializeTonight() {
//    viewModelScope.launch {
//        tonight.value = getTonightFromDatabase()
//    }
//}
