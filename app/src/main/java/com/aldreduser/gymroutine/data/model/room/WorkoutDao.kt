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
    suspend fun insert(workout: Workout)
    @Update
    suspend fun update(workout: Workout)
    @Delete
    suspend fun delete(workout: Workout)

    @Query(
        "SELECT * FROM workout_table " +
                "WHERE workout_group = :group " +
                "ORDER BY id ASC"
    )
    suspend fun getWorkoutsOfThisGroup(group: String): List<Workout>
}