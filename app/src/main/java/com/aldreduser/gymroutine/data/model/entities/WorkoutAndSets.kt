package com.aldreduser.gymroutine.data.model.entities

import androidx.room.Embedded
import androidx.room.Relation

class WorkoutAndSets (
    @Embedded val workout: Workout,
    @Relation(
        parentColumn = "id",
        entityColumn = "workout_id"
    )
    val workoutSets: List<WorkoutSet>
)