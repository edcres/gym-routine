package com.aldreduser.gymroutine.data.model.room

import androidx.room.*
import com.aldreduser.gymroutine.data.model.entities.Workout
import com.aldreduser.gymroutine.data.model.entities.WorkoutAndSets
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {

    @Query("SELECT * FROM workout_table ORDER BY id ASC")
    fun getAlphabetizedWorkouts(): Flow<List<Workout>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(workout: Workout): Long
    @Update
    suspend fun update(workout: Workout)
    @Query(
        "UPDATE workout_table " +
                "SET workout_group = :groupSelected " +
                "WHERE id = :workoutId"
    )
    suspend fun updateWorkout(workoutId: Long, groupSelected: String)
    @Delete
    suspend fun delete(workout: Workout)

    @Query(
        "SELECT * FROM workout_table " +
                "WHERE workout_group = :group " +
                "ORDER BY id ASC"
    )
    suspend fun getWorkoutsOfThisGroup(group: String): List<Workout>

    @Query(
        "SELECT workout_name FROM workout_table " +
                "WHERE workout_group = :group " +
                "ORDER BY id ASC"
    )
    suspend fun  getWorkoutNamesOfThisGroup(group: String): List<String>
    @Query(
        "SELECT workout_name FROM workout_table " +
                "WHERE id = :workoutId"
    )
    suspend fun  getWorkoutName(workoutId: Long): String
}