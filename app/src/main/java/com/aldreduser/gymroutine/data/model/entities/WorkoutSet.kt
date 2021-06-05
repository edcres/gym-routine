package com.aldreduser.gymroutine.data.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// todo: typo 'workoutPluSet' -> 'workoutPlusSet'

@Entity(tableName = "set_table")
data class WorkoutSet (
    @PrimaryKey
    @ColumnInfo(name = "workout_plus_set")
    val workoutPluSet: String = "",
    @ColumnInfo(name = "workout_name")   // relation to workout group table
    val workoutName: String = "",
    @ColumnInfo(name = "reps")
    val reps: Int = 0,
    @ColumnInfo(name = "weight")
    val weight: Double = 0.0,
)