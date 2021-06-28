package com.aldreduser.gymroutine.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.aldreduser.gymroutine.data.model.entities.WorkoutSet
import com.aldreduser.gymroutine.data.model.entities.Workout
import com.aldreduser.gymroutine.data.model.entities.WorkoutGroup

// possible bug: maybe these shouldn't be TextViews

//todo: learn about sqlite relationships and display the correct weights and reps
// per workout -> workout set -> reps
// per workout -> workout set -> weight

@BindingAdapter("workoutName")
fun TextView.setWorkoutGroupName(item: Workout) {
    item?.let{
        text = item.thisWorkoutName
    }
}

@BindingAdapter("workoutReps")
fun TextView.setWorkoutReps(item: WorkoutSet) {
    item?.let {
        text = item.reps.toString()
    }
}

@BindingAdapter("workoutWeight")
fun TextView.setWorkoutWeight(item: WorkoutSet) {
    item?.let {
        text = item.weight.toString()
    }
}
