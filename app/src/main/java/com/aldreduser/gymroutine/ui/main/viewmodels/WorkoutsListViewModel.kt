package com.aldreduser.gymroutine.ui.main.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.aldreduser.gymroutine.data.WorkoutRepository
import com.aldreduser.gymroutine.data.model.entities.Workout
import com.aldreduser.gymroutine.data.model.entities.WorkoutGroup
import com.aldreduser.gymroutine.data.model.entities.WorkoutSet
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

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

//todo: delete below code
//class WorkoutsListViewModelFactor(private val repository: WorkoutRepository) : ViewModelProvider.Factory{
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(WorkoutsListViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            val mainActivityViewModel = WorkoutsListViewModel(repository)
//            return mainActivityViewModel as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}