package com.aldreduser.gymroutine.data

import androidx.annotation.WorkerThread
import com.aldreduser.gymroutine.data.model.entities.Workout
import com.aldreduser.gymroutine.data.model.entities.WorkoutGroup
import com.aldreduser.gymroutine.data.model.room.WorkoutDao
import com.aldreduser.gymroutine.data.model.room.WorkoutGroupDao
import com.aldreduser.gymroutine.data.model.room.WorkoutSetDao
import kotlinx.coroutines.flow.Flow

// Repo only has access to the DAOs, not the database.
class WorkoutsRepository(
    private val workoutGroupDao: WorkoutGroupDao,
    private val workoutDao: WorkoutDao,
    private val workoutSetDao: WorkoutSetDao) {

    // todo: Add all the other DAOs with all their query functions. ()

    //Workout Group
    // getAlphabetizedWorkoutGroups
    val allWorkoutGroups: Flow<List<WorkoutGroup>> = workoutGroupDao.getAlphabetizedWorkoutGroups()
    // insert
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(workoutGroup: WorkoutGroup) {
        workoutGroupDao.insert(workoutGroup)
    }
    // delete (probably similar to insert)
    // workoutGroup+workout relationship (maybe, idk how this would work)

    //Workout
    // getAlphabetizedWorkouts
    val allWorkouts: Flow<List<Workout>> = workoutDao.getAlphabetizedWorkouts()
    // insert
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(workout: Workout) {
        workoutDao.insert(workout)
    }
    // update
    // delete
    // workout+workoutSet relationship (maybe, idk how this would work)

    //Workout Set
    // getAlphabetizedSets
    // insert
    // update
    // delete
}