package com.aldreduser.gymroutine.data.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// relationship between entities
// https://developer.android.com/training/data-storage/room/relationships

@Entity(tableName = "workout_group_table")
data class WorkoutGroup (
    @PrimaryKey
    @ColumnInfo(name = "groupName")  // the WorkoutGroup will be identified by the group name
    val groupName: String = "",            // chest, legs, back

//    @ColumnInfo(name = "workoutName")
//    val workoutName: String = ""
)
