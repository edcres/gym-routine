package com.aldreduser.gymroutine.data.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_table")
data class Workout (
    @PrimaryKey
    @ColumnInfo(name = "this_workout_name")
    val thisWorkoutName: String = "",
    @ColumnInfo(name = "workout_group")  // relation to workout group table
    val workoutGroup: String = "",
    @ColumnInfo(name = "sets")
    val sets: Int = 0
)