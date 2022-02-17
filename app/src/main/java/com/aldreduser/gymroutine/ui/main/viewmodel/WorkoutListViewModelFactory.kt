package com.aldreduser.gymroutine.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

// todo: probably get rid of the viewModel factory
// 'WorkoutsListViewModelFactory' returns an instance of the 'WorkoutsListViewModel' class
//class WorkoutListViewModelFactory : ViewModelProvider.Factory{
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(WorkoutListViewModel::class.java)) {
//            val mainActivityViewModel = WorkoutListViewModel()
//            return mainActivityViewModel as T
//        } else { throw IllegalArgumentException("Unknown ViewModel class") }
//    }
//}
