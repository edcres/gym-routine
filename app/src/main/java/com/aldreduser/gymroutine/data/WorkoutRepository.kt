package com.aldreduser.gymroutine.data

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.room.Room
import com.aldreduser.gymroutine.data.model.entities.Workout
import com.aldreduser.gymroutine.data.model.entities.WorkoutGroup
import com.aldreduser.gymroutine.data.model.entities.WorkoutSet
import com.aldreduser.gymroutine.data.model.room.WorkoutsRoomDatabase
import kotlinx.coroutines.flow.Flow

// Repo only has access to the DAOs, not the database.
class WorkoutRepository(private val database: WorkoutsRoomDatabase)
{
    // todo: Access the DAOs through the database
    // todo: Add all the other DAOs with all their query functions. ()
    //Workout Group
    // getAlphabetizedWorkoutGroups

    val allWorkoutGroups: Flow<List<WorkoutGroup>> = database.workoutGroupDao().getAlphabetizedWorkoutGroups()
    // insert
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(workoutGroup: WorkoutGroup) {
        database.workoutGroupDao().insert(workoutGroup)
    }
    // delete (probably similar to insert)
    // workoutGroup+workout relationship (maybe, idk how this would work)

    //Workout
    // getAlphabetizedWorkouts
    val allWorkouts: Flow<List<Workout>> = database.workoutDao().getAlphabetizedWorkouts()
    // insert
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(workout: Workout) {
        database.workoutDao().insert(workout)
    }
    // update
    // delete
    // workout+workoutSet relationship (maybe, idk how this would work)

    //Workout Set
    // getAlphabetizedSets
    val allWorkoutSets: Flow<List<WorkoutSet>> = database.workoutSetDao().getAlphabetizedSets()
    // insert
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(workoutSet: WorkoutSet) {
        database.workoutSetDao().insert(workoutSet)
    }
    // update
    // delete

    // Singleton for repository
    companion object {

        private var instance: WorkoutRepository? = null

        // Helper function to get the repository.
        fun getInstance(database: WorkoutsRoomDatabase): WorkoutRepository {
            // Multiple threads can ask for the database at the same time, ensure we only initialize
            //  it once by using synchronized. Only one thread may enter a synchronized block at a time.
            return instance ?: synchronized(this) {
                instance ?: WorkoutRepository(database).also {
                    instance = it
                }
            }
        }
    }
}
