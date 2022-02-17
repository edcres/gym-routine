package com.aldreduser.gymroutine.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aldreduser.gymroutine.data.WorkoutRepository
import java.lang.IllegalArgumentException

// 'WorkoutsListViewModelFactory' returns an instance of the 'WorkoutsListViewModel' class
// the viewModelFactory will be responsible for instantiating the ScoreViewModel object.
class WorkoutListViewModelFactory(
        private val repository: WorkoutRepository,
        private val application: Application) : ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkoutListViewModel::class.java)) {
            val mainActivityViewModel = WorkoutListViewModel(repository, application)
            return mainActivityViewModel as T
        } else { throw IllegalArgumentException("Unknown ViewModel class") }
    }
}
