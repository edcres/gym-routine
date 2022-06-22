package com.aldreduser.gymroutine.data.model.room

import androidx.room.*
import com.aldreduser.gymroutine.data.model.entities.Workout
import com.aldreduser.gymroutine.data.model.entities.WorkoutAndSets
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {

    @Query("SELECT * FROM workout_table ORDER BY workout_name ASC")
    fun getWorkoutsByName(): Flow<List<Workout>>

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

    @Query(
        "UPDATE workout_table " +
                "SET muscles_targeted = :muscles, notes = :notes " +
                "WHERE id = :workoutId"
    )
    suspend fun updateWorkoutNotes(workoutId: Long, muscles: String, notes: String)

    @Delete
    suspend fun delete(workout: Workout)

    @Query("SELECT * FROM workout_table ORDER BY workout_name ASC")
    suspend fun getWorkoutsByNameOnce(): List<Workout>

    @Query(
        "SELECT * FROM workout_table " +
                "WHERE workout_group = :group " +
                "ORDER BY workout_name ASC"
    )
    suspend fun getWorkoutsOfThisGroup(group: String): List<Workout>

    @Query(
        "SELECT workout_name FROM workout_table " +
                "WHERE workout_group = :group " +
                "ORDER BY workout_name ASC"
    )
    suspend fun getWorkoutNamesOfThisGroup(group: String): List<String>

    @Query(
        "SELECT workout_name FROM workout_table " +
                "WHERE id = :workoutId"
    )
    suspend fun getWorkoutName(workoutId: Long): String

    @Query(
        "SELECT workout_group FROM workout_table " +
                "WHERE id = :workoutId"
    )
    suspend fun getGroupOfThisWorkout(workoutId: Long): String

//    @Transaction
//    @Query("SELECT * FROM workout_table")
//    suspend fun getWorkoutWithSets(): List<WorkoutAndSets>
}