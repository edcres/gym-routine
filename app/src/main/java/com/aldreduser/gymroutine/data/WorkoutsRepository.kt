package com.aldreduser.gymroutine.data

import androidx.annotation.WorkerThread
import com.aldreduser.gymroutine.data.model.entities.Workout
import com.aldreduser.gymroutine.data.model.entities.WorkoutGroup
import com.aldreduser.gymroutine.data.model.entities.WorkoutSet
import com.aldreduser.gymroutine.data.model.room.WorkoutsRoomDatabase
import kotlinx.coroutines.flow.Flow

// Repo only has access to the DAOs, not the database.
class WorkoutsRepository(private val database: WorkoutsRoomDatabase) {

    // get names from repository
    //names of WorkoutGroups
    // todo: might have to make these vars into functions so they are refreshed every time they are called
    val allWorkoutGroups: Flow<List<WorkoutGroup>> =
        database.workoutGroupDao().getAlphabetizedWorkoutGroups()
    //names of Workouts
    val allWorkouts: Flow<List<Workout>> = database.workoutDao().getAlphabetizedWorkouts()
    //names of WorkoutSets
    val allWorkoutSets: Flow<List<WorkoutSet>> = database.workoutSetDao().getAlphabetizedSets()

    // insert
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(workoutGroup: WorkoutGroup) {
        database.workoutGroupDao().insert(workoutGroup)
    }
    // delete (probably similar to insert)
    // workoutGroup+workout relationship (maybe, idk how this would work)

    // WORKOUT
    // insert
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(workout: Workout) {
        database.workoutDao().insert(workout)
    }
    // update
    // delete
    // workout+workoutSet relationship (maybe, idk how this would work)

    // WORKOUT SET
    // insert
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(workoutSet: WorkoutSet) {
        database.workoutSetDao().insert(workoutSet)
    }
    // update
    // delete
}
