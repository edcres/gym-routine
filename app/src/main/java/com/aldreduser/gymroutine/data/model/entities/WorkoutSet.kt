package com.aldreduser.gymroutine.data.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "set_table",
    foreignKeys = [
        ForeignKey(
            entity = Workout::class,
            parentColumns = ["id"],
            childColumns = ["workout_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class WorkoutSet (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,
    @ColumnInfo(name = "workout_plus_set_id")
    val workoutPlusSetId: String = "", //"1-1"
    @ColumnInfo(name = "workout_id")
    val workoutId: Long,
    @ColumnInfo(name = "workout_name")
    val workoutName: String = "",
    @ColumnInfo(name = "set")
    val set: Int = 1,
    @ColumnInfo(name = "reps")
    var reps: Int = 0,
    @ColumnInfo(name = "weight")
    var weight: Double = 0.0,
)