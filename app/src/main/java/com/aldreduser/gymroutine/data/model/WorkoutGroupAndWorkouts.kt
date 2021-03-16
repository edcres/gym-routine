package com.aldreduser.gymroutine.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class WorkoutGroupAndWorkouts(
    @Embedded val workoutGroup: WorkoutGroup,
    @Relation(
        parentColumn = "groupName",
        entityColumn = "workoutGroup"
    )
    val workouts: List<Workout>
)