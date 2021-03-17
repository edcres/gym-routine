package com.aldreduser.gymroutine.data.model.room

import androidx.room.*
import com.aldreduser.gymroutine.data.model.WorkoutGroup
import com.aldreduser.gymroutine.data.model.WorkoutGroupAndWorkouts

@Dao
interface WorkoutGroupDao {

    // getting group names alphabetically
    @Query("SELECT * FROM workout_group_table ORDER BY groupName ASC")
    fun getAlphabetizedGroupNames(): List<WorkoutGroup>

    // insert: when user chooses to create a group when adding or editing a workout.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(workoutGroup: WorkoutGroup)

    // delete group: happens when all workouts in that group are deleted.
    @Delete
    suspend fun deleteWorkoutGroup(workoutGroup: WorkoutGroup)

    // for relationship between workoutGroup and workout
    // returns all the instances in which the workoutCategory is the same as the categoryName (ie. all legs with legs, and all chest with chest)
    //  -that way i can access all the workouts that are chest by looking up the group name
    @Transaction
    @Query("SELECT * FROM workout_group_table")
    fun getWorkoutGroupsWithWorkouts(): List<WorkoutGroupAndWorkouts>
}