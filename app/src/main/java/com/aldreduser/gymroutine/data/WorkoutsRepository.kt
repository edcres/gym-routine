package com.aldreduser.gymroutine.data

import androidx.annotation.WorkerThread
import com.aldreduser.gymroutine.data.model.entities.Workout
import com.aldreduser.gymroutine.data.model.entities.WorkoutGroup
import com.aldreduser.gymroutine.data.model.entities.WorkoutSet
import com.aldreduser.gymroutine.data.model.room.WorkoutsRoomDatabase
import kotlinx.coroutines.flow.Flow

// todo: change the names of query functions to make them more specific

// Repo only has access to the DAOs, not the database.
class WorkoutsRepository(private val database: WorkoutsRoomDatabase) {

    // todo: Add all the other DAOs with all their query functions. ()

    // get names from repository
    //names of WorkoutGroups
    val allWorkoutGroups: Flow<List<WorkoutGroup>> = database.workoutGroupDao().getAlphabetizedWorkoutGroups()
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

    // todo: probably get rid of this companion object
    // Singleton for repository
//    companion object {
//
//        private var instance: WorkoutsRepository? = null
//
//        // Helper function to get the repository.
//        fun getInstance(database: WorkoutsRoomDatabase): WorkoutsRepository {
//            // Multiple threads can ask for the database at the same time, ensure we only initialize
//            //  it once by using synchronized. Only one thread may enter a synchronized block at a time.
//            return instance ?: synchronized(this) {
//                instance ?: WorkoutsRepository(database).also {
//                    instance = it
//                }
//            }
//        }
//    }
}
