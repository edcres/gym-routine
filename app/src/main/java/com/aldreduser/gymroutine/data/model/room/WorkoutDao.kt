package com.aldreduser.gymroutine.data.model.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.aldreduser.gymroutine.data.model.WorkoutAndSets

@Dao
interface WorkoutDao {

    // for relationship between workout and set
    @Transaction
    @Query("SELECT * FROM workout_table")
    fun getWorkoutWithSets(): List<WorkoutAndSets>
}