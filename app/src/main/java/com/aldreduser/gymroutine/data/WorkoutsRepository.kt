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

    private val tag = "WRepo_TAG"
    val allWorkoutGroups: Flow<List<WorkoutGroup>> =
        database.workoutGroupDao().getAlphabetizedWorkoutGroups()
    val allWorkouts: Flow<List<Workout>> = database.workoutDao().getAlphabetizedWorkouts()
    val allWorkoutSets: Flow<List<WorkoutSet>> = database.workoutSetDao().getAlphabetizedSets()

    @WorkerThread
    suspend fun insert(workoutGroup: WorkoutGroup, workoutId: Long?) {
        // Inserts a WorkoutGroup
        database.workoutGroupDao().insert(workoutGroup)
        if (workoutId != null) {
            Log.d(tag, "insert group: workout id is null")
            database.workoutDao().updateWorkout(workoutId, workoutGroup.groupName)
        }
    }
    @WorkerThread
    suspend fun insert(workout: Workout): Long {
        Log.d(tag, "insert workout: called")
        return database.workoutDao().insert(workout)
    }
    @WorkerThread
    suspend fun insert(workoutSet: WorkoutSet) {
        Log.d(tag, "insert set: called")
        database.workoutSetDao().insert(workoutSet)
    }

    @WorkerThread
    suspend fun update(workout: Workout) {
        database.workoutDao().update(workout)
    }
    @WorkerThread
    suspend fun updateWorkout(workoutId: Long, groupSelected: String) {
        database.workoutDao().updateWorkout(workoutId, groupSelected)
    }
    @WorkerThread
    suspend fun update(set: WorkoutSet) {
        database.workoutSetDao().update(set)
    }
    @WorkerThread
    suspend fun updateSetOnSets(workoutId: Long, startingSet: Int, setsOfThisWorkout: List<WorkoutSet>) {
        val setsToUpdate =
            database.workoutSetDao().getSetsProceedingSetNum(workoutId, startingSet).toMutableList()
        setsToUpdate.forEach {
            it.set--
        }
        database.workoutSetDao().update(setsToUpdate)
        var setsToUpdateString = ""
        setsToUpdate.forEach {
            setsToUpdateString = "$setsToUpdateString\n${it.id}\t${it.set}"
        }
    }
    @WorkerThread
    suspend fun updateWorkoutOnSets(workoutId: Long, newWorkout: String) {
        database.workoutSetDao().updateWorkoutOnSets(workoutId, newWorkout)
    }

    @WorkerThread
    suspend fun deleteGroup(groupName: String) {
        database.workoutGroupDao().delete(groupName)
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
    suspend fun getAllWorkouts(): List<Workout> {
        return database.workoutDao().getAlphabetizedWorkoutsOnce()
    }
    @WorkerThread
    suspend fun getWorkoutsOfThisGroup(groupName: String): List<Workout> {
        return database.workoutDao().getWorkoutsOfThisGroup(groupName)
    }
    @WorkerThread
    suspend fun getSetsOfWorkout(workoutId: Long): List<WorkoutSet> {
        return database.workoutSetDao().getSetsOfWorkout(workoutId)
    }
    @WorkerThread
    suspend fun groupHasWorkouts(groupName: String): Boolean {
        // If the list is empty, returns false, else return true
        return database.workoutDao().getWorkoutNamesOfThisGroup(groupName).isNotEmpty()
    }
    @WorkerThread
    suspend fun getLastSet(workoutId: Long): WorkoutSet {
        val setsOfWorkout = database.workoutSetDao().getSetsOfWorkout(workoutId)
        return if (setsOfWorkout.isNotEmpty()) {
            setsOfWorkout.last()
        } else WorkoutSet(
            workoutId = workoutId,
            workoutName = database.workoutDao().getWorkoutName(workoutId),
            set = 0,
        )
    }
    @WorkerThread
    suspend fun getWorkoutName(workoutId: Long): String {
        return database.workoutDao().getWorkoutName(workoutId)
    }
    @WorkerThread
    suspend fun getGroupOfWorkout(workoutId: Long): String {
        return database.workoutDao().getGroupOfThisWorkout(workoutId)
    }
}