package com.aldreduser.gymroutine.data.model.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.aldreduser.gymroutine.data.model.WorkoutGroupAndWorkouts

@Dao
interface WorkoutGroupDao {

    // for relationship between workoutGroup and workout
    // returns all the instances in which the workoutCategory is the same as the categoryName (ie. all legs with legs, and all chest with chest)
    //  -that way i can access all the workouts that are chest by looking up the group name
    @Transaction
    @Query("SELECT * FROM workout_group_table")
    fun getWorkoutGroupsWithWorkouts(): List<WorkoutGroupAndWorkouts>
}