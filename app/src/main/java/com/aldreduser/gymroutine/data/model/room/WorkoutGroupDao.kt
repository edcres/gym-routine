package com.aldreduser.gymroutine.data.model.room

import androidx.room.*
import com.aldreduser.gymroutine.data.model.entities.WorkoutGroup
import com.aldreduser.gymroutine.data.model.entities.WorkoutGroupAndWorkouts
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutGroupDao {

    @Query("SELECT * FROM workout_group_table ORDER BY group_name ASC")
    fun getAlphabetizedWorkoutGroups(): Flow<List<WorkoutGroup>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(workoutGroup: WorkoutGroup)

    @Delete
    suspend fun delete(workoutGroup: WorkoutGroup)
}