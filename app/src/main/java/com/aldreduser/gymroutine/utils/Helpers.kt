package com.aldreduser.gymroutine.utils

import com.aldreduser.gymroutine.data.model.entities.WorkoutGroup
import com.aldreduser.gymroutine.data.model.entities.WorkoutSet

const val GLOBAL_TAG = "Global__TAG"
const val FIRST_TAB_TITLE = "All Workouts"
const val NEW_GROUP = "New Group"
const val CHOOSE_GROUP = "Choose Group"
val DUMMY_SETS = listOf(
    WorkoutSet(0, 0),WorkoutSet(0, 0),WorkoutSet(0, 0),WorkoutSet(0, 0)
)

fun getChooseGroupList(groups: List<String>): List<String> {
    val spinnerList = mutableListOf(CHOOSE_GROUP)
    groups.forEach {
        spinnerList.add(it)
    }
    spinnerList.add(NEW_GROUP)
    return spinnerList
}

fun findDifferentGroups(groups: List<WorkoutGroup>, groupNames: List<String>): List<String> {
    // more groups than groupNames
    // it should only be one item
    return groupsToStrings(groups).minus(groupNames)
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

fun takeOutZeros(num: Double): String {
    // if it's 23.0, returns 23
    return if (num.toString().contains(".")){
        val decimals = num.toString().split(".").last()
        if (decimals.length == 1 && decimals.first() == '0') num.toInt().toString()
        else num.toString()
    } else {
        num.toString()
    }
}