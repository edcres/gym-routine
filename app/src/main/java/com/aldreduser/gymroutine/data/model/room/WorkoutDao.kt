package com.aldreduser.gymroutine.data.model.room

import androidx.room.*
import com.aldreduser.gymroutine.data.model.entities.Workout
import com.aldreduser.gymroutine.data.model.entities.WorkoutAndSets
import kotlinx.coroutines.flow.Flow

// todo: make these suspend functions, probably unless it uses livedata

@Dao
interface WorkoutDao {

    // getting workout names alphabetically
    @Query("SELECT * FROM workout_table ORDER BY this_workout_name ASC")
    fun getAlphabetizedWorkouts(): Flow<List<Workout>>

    // Insert
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(workout: Workout)

    // Update Item
    @Update
    suspend fun updateWorkout(workout: Workout)

    // Delete item
    @Delete
    suspend fun deleteWorkout(workout: Workout)

    // relationship between workout and set
    @Transaction
    @Query("SELECT * FROM workout_table")
    suspend fun getWorkoutWithSets(): List<WorkoutAndSets>
}