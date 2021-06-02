package com.aldreduser.gymroutine.ui.main.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.aldreduser.gymroutine.data.WorkoutRepository
import com.aldreduser.gymroutine.data.model.entities.Workout
import com.aldreduser.gymroutine.data.model.entities.WorkoutGroup
import com.aldreduser.gymroutine.data.model.entities.WorkoutSet
import kotlinx.coroutines.launch

// todo: use 'viewModelScope.launch' when retrieving data from repository

// The ViewModel will transform the data from the Repository,
//  from Flow to LiveData and exposes the list of words as LiveData to the UI.
class WorkoutsListViewModel(
        private val repository: WorkoutRepository,
        private val application: Application) : ViewModel() {

    // Workout Group //
    // get all workout groups
    val allWorkoutGroups: LiveData<List<WorkoutGroup>> = repository.allWorkoutGroups.asLiveData()
    // insert
    fun insertWorkoutGroup(workoutGroup: WorkoutGroup) = viewModelScope.launch {
        repository.insert(workoutGroup)
    }

    // Workout //
    // get all workouts
    val allWorkouts: LiveData<List<Workout>> = repository.allWorkouts.asLiveData()
    // insert
    fun insertWorkout(workout: Workout) = viewModelScope.launch {
        repository.insert(workout)
    }

    // Workout Set //
    // get all workout sets
    val allWorkoutSets: LiveData<List<WorkoutSet>> = repository.allWorkoutSets.asLiveData()
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
