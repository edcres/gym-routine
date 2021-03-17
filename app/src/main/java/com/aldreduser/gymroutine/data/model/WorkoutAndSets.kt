package com.aldreduser.gymroutine.data.model

import androidx.room.Embedded
import androidx.room.Relation

class WorkoutAndSets (
    @Embedded val workout: Workout,
    @Relation(
        parentColumn = "thisWorkoutName",
        entityColumn = "workoutName"
    )
    val workoutSets: List<WorkoutSet>
)