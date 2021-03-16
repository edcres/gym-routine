package com.aldreduser.gymroutine.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "set_table")
data class Set (
    @PrimaryKey
    @ColumnInfo(name = "workoutPluSet")
    val workoutPluSet: String = "",
    @ColumnInfo(name = "workoutName")   // relation to workout group table
    val workoutName: String = "",
    @ColumnInfo(name = "reps")
    val reps: Int = 0,
    @ColumnInfo(name = "weight")
    val weight: Double = 0.0,
)