package com.aldreduser.gymroutine.utils

import com.aldreduser.gymroutine.data.model.entities.WorkoutGroup
import com.aldreduser.gymroutine.data.model.entities.WorkoutSet

const val GLOBAL_TAG = "Global TAG"
const val FIRST_TAB_TITLE = "All Workouts"
const val NEW_GROUP = "New Group"

fun findDifferentGroup(groups: List<WorkoutGroup>, groupNames: List<String>): String {
    // more groups than groupNames
    val difference = groupsToStrings(groups).minus(groupNames)
    // it should only be one item
    return difference[0]
}

fun findDifferentName(groupNames: List<String>, groups: List<WorkoutGroup>): String {
    // more groupNames than groups
    val difference = groupNames.minus(groupsToStrings(groups))
    // it should only be one item
    return difference[0]
}

fun groupsToStrings(groups: List<WorkoutGroup>): List<String> {
    val groupStrings = mutableListOf<String>()
    groups.forEach { groupStrings.add(it.groupName) }
    return groupStrings
}