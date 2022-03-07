package com.aldreduser.gymroutine.data.model.room

import androidx.room.*
import com.aldreduser.gymroutine.data.model.entities.Workout
import com.aldreduser.gymroutine.data.model.entities.WorkoutAndSets
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {

    // getting workout names alphabetically
    @Query("SELECT * FROM workout_table ORDER BY id ASC")
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