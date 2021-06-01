package com.aldreduser.gymroutine.data.model.entities

import androidx.room.Embedded
import androidx.room.Relation

class WorkoutAndSets (
    @Embedded val workout: Workout,
    @Relation(
        parentColumn = "this_workout_mame",
        entityColumn = "workout_name"
    )
    val workoutSets: List<WorkoutSet>
)