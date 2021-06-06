package com.aldreduser.gymroutine.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.aldreduser.gymroutine.data.model.entities.WorkoutSet
import com.aldreduser.gymroutine.data.model.entities.Workout
import com.aldreduser.gymroutine.data.model.entities.WorkoutGroup

// possible bug: maybe these shouldn't be TextViews

@BindingAdapter("workoutGroupName")
fun TextView.setWorkoutGroupName(item: Workout) {
    item?.let{
        text = item.workoutGroup
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

//@BindingAdapter("setWorkoutGroupName")
//fun TextView.setWorkoutGroupName(item: WorkoutGroup) {
//    item?.let{
//        text = convertToString(item.groupName)
//    }
//}