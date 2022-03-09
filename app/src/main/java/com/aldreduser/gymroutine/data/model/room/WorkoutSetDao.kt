package com.aldreduser.gymroutine.data.model.room

import androidx.room.*
import com.aldreduser.gymroutine.data.model.entities.WorkoutSet
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutSetDao {
    // getting set names alphabetically (ie: "incline bench 1")
    @Query("SELECT * FROM set_table ORDER BY id ASC")
    fun getAlphabetizedSets(): Flow<List<WorkoutSet>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(set: WorkoutSet)
    @Update
    suspend fun update(set: WorkoutSet)
    @Delete
    suspend fun delete(set: WorkoutSet)

    @Query(
        "UPDATE set_table " +
                "SET workout_name = :newWorkout " +
                "WHERE workout_id = :workoutId"
    )
    suspend fun updateWorkoutOnSets(workoutId: Long, newWorkout: String)

    @Query(
        "UPDATE set_table " +
                "SET `set` = :newSetNum " +
                "WHERE `set` = :oldSetNum"
    )
    suspend fun updateSetOnSets(oldSetNum: Int, newSetNum: Int)

    @Query(
        "SELECT * FROM set_table " +
                "WHERE workout_id = :workoutId " +
                "ORDER BY `set` ASC"
    )
    suspend fun getSetsOfWorkout(workoutId: Long): List<WorkoutSet>

//    @Query(
//        "SELECT `set` FROM set_table " +
//                "WHERE workout_id = :workoutId " +
//                "ORDER BY `set` ASC"
//    )
//    suspend fun getSetNumList(workoutId: Long): List<Int>
}