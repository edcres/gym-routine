package com.aldreduser.gymroutine.data.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_group_table")
data class WorkoutGroup (
    @PrimaryKey
    @ColumnInfo(name = "group_name")
    val groupName: String = ""
)
