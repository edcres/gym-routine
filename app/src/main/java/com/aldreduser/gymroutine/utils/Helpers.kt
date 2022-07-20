package com.aldreduser.gymroutine.utils

import com.aldreduser.gymroutine.data.model.entities.WorkoutGroup
import com.aldreduser.gymroutine.data.model.entities.WorkoutSet

const val GLOBAL_TAG = "Global__TAG"
const val FIRST_TAB_TITLE = "All Workouts"
const val NEW_GROUP = "New Group"
const val CHOOSE_GROUP = "Choose Group"

val DUMMY_SETS = listOf(
    WorkoutSet(0, 0), WorkoutSet(0, 0),
    WorkoutSet(0, 0), WorkoutSet(0, 0)
)

fun getChooseGroupList(groups: List<String>): List<String> {
    val spinnerList = mutableListOf(CHOOSE_GROUP)
    groups.forEach { spinnerList.add(it) }
    spinnerList.add(NEW_GROUP)
    return spinnerList
}

// Check the difference between groups and group names.
// More groups than groupNames
// It should only be one item.
fun findDifferentGroups(groups: List<WorkoutGroup>, groupNames: List<String>): List<String> =
    groupsToStrings(groups).minus(groupNames)

// Check the difference between groups and group names.
// More groupNames than groups.
// It should only be one item.
fun findDifferentName(groupNames: List<String>, groups: List<WorkoutGroup>): String =
    groupNames.minus(groupsToStrings(groups))[0]

fun groupsToStrings(groups: List<WorkoutGroup>): List<String> {
    // Return a list of group names.
    val groupStrings = mutableListOf<String>()
    groups.forEach { groupStrings.add(it.groupName) }
    return groupStrings
}

fun takeOutZeros(num: Double): String {
    // If it's 23.0, return 23.
    return if (num.toString().contains(".")) {
        val decimals = num.toString().split(".").last()
        if (decimals.length == 1 && decimals.first() == '0') num.toInt().toString()
        else num.toString()
    } else num.toString()
}