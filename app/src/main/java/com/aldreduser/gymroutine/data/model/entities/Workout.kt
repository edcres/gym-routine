package com.aldreduser.gymroutine.data.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.aldreduser.gymroutine.utils.FIRST_TAB_TITLE

@Entity(
    tableName = "workout_table",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutGroup::class,
            parentColumns = ["group_name"],
            childColumns = ["workout_group"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Workout (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,
    @ColumnInfo(name = "workout_name")
    var workoutName: String = "",
    @ColumnInfo(name = "workout_group")  // relation to workout group table
    var workoutGroup: String = FIRST_TAB_TITLE
//    @ColumnInfo(name = "sets")
//    var sets: Int = 1
)