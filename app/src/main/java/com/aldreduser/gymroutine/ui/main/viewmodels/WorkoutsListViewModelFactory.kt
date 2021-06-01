package com.aldreduser.gymroutine.ui.main.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aldreduser.gymroutine.data.WorkoutRepository
import java.lang.IllegalArgumentException

// 'WorkoutsListViewModelFactory' returns an instance of the 'WorkoutsListViewModel' class
// the viewModelFactory will be responsible for instantiating the ScoreViewModel object.
// To send the parameter dependencies to the viewModel.
class WorkoutsListViewModelFactory(
        private val repository: WorkoutRepository,
        private val application: Application) : ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkoutsListViewModel::class.java)) {
            val mainActivityViewModel = WorkoutsListViewModel(repository, application)
            return mainActivityViewModel as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
