package com.aldreduser.gymroutine.data

import android.util.Log
import androidx.annotation.WorkerThread
import com.aldreduser.gymroutine.data.model.entities.Workout
import com.aldreduser.gymroutine.data.model.entities.WorkoutGroup
import com.aldreduser.gymroutine.data.model.entities.WorkoutSet
import com.aldreduser.gymroutine.data.model.room.WorkoutsRoomDatabase
import kotlinx.coroutines.flow.Flow

// Repo only has access to the DAOs, not the database.
class WorkoutsRepository(private val database: WorkoutsRoomDatabase) {

    private val tag = "WRepository TAG"
    val allWorkoutGroups: Flow<List<WorkoutGroup>> =
        database.workoutGroupDao().getAlphabetizedWorkoutGroups()
    val allWorkouts: Flow<List<Workout>> = database.workoutDao().getAlphabetizedWorkouts()
    val allWorkoutSets: Flow<List<WorkoutSet>> = database.workoutSetDao().getAlphabetizedSets()

    @WorkerThread
    suspend fun insert(workoutGroup: WorkoutGroup) {
        database.workoutGroupDao().insert(workoutGroup)
    }
    @WorkerThread
    suspend fun insert(workout: Workout): Long {
        database.workoutDao().insert(workout)
    }
    @WorkerThread
    suspend fun insert(workoutSet: WorkoutSet) {
        database.workoutSetDao().insert(workoutSet)
    }

    @WorkerThread
    suspend fun update(workout: Workout) {
        database.workoutDao().update(workout)
    }
    @WorkerThread
    suspend fun update(set: WorkoutSet) {
        database.workoutSetDao().update(set)
    }
    @WorkerThread
    suspend fun updateSetOnSets(startingSet: Int, setsOfThisWorkout: List<WorkoutSet>) {
        if(setsOfThisWorkout.isNotEmpty()) {
            for (i in 1..setsOfThisWorkout.size) {
                val thisSet = setsOfThisWorkout[i - 1].set
                if (startingSet <= thisSet) {
                    database.workoutSetDao().updateSetOnSets(thisSet, thisSet)
                }
            }
        } else {
            Log.i(tag, "There are no more sets to update.")
        }
    }
    @WorkerThread
    suspend fun updateWorkoutOnSets(workoutId: Long, newWorkout: String) {
        database.workoutSetDao().updateWorkoutOnSets(workoutId, newWorkout)
    }

    @WorkerThread
    suspend fun deleteGroup(group: WorkoutGroup) {
        database.workoutGroupDao().delete(group)
    }
    @WorkerThread
    suspend fun deleteWorkout(workout: Workout) {
        database.workoutDao().delete(workout)
    }
    @WorkerThread
    suspend fun deleteSet(set: WorkoutSet) {
        database.workoutSetDao().delete(set)
    }

    @WorkerThread
    suspend fun getWorkoutsOfThisGroup(groupName: String): List<WST1Workout> {
        return database.workoutDao().getWorkoutsOfThisGroup(groupName)
    }
    @WorkerThread
    suspend fun getSetsOfWorkout(workoutId: Long): List<WST1Set> {
        return database.workoutSetDao().getSetsOfWorkout(workoutId)
    }
    @WorkerThread
    suspend fun getNextSetNum(workoutId: Long): Int {
        return database.workoutSetDao().getSetNumList(workoutId).size
    }
    @WorkerThread
    suspend fun groupHasWorkouts(groupName: String): Boolean {
        // If list is empty, returns false, else return true
        return database.workoutDao().getWorkoutsOfThisGroup(groupName).isNotEmpty()
    }
}