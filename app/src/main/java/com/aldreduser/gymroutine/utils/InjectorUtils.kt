package com.aldreduser.gymroutine.utils

import android.content.Context
import com.aldreduser.gymroutine.data.WorkoutRepository
import com.aldreduser.gymroutine.data.model.room.WorkoutsRoomDatabase

// This is to pass the repository to the viewmodel through the viewmodelFactory
fun provideRepository(context: Context): WorkoutRepository {
    // I think this executor is for multi-threading, so that the repository is passed in a different thread
    val executors = AppExecutors.getInstance()
    val db = WorkoutsRoomDatabase.getDatabaseInstance(context)
    return WorkoutRepository.getRepositoryInstance(db, executors)
}