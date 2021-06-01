package com.aldreduser.gymroutine.data.model.room

import androidx.room.*
import com.aldreduser.gymroutine.data.model.entities.WorkoutSet
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutSetDao {
    // getting set names alphabetically (ie: "incline bench 1")
    @Query("SELECT * FROM set_table ORDER BY workout_plus_set ASC")
    fun getAlphabetizedSets(): Flow<List<WorkoutSet>>

    // Insert
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(set: WorkoutSet)

    // Update Item
    @Update
    suspend fun updateSet(set: WorkoutSet)

    // Delete Item
    @Delete
    suspend fun deleteSet(set: WorkoutSet)
}