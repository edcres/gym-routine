package com.aldreduser.gymroutine.utils

import com.aldreduser.gymroutine.data.model.entities.WorkoutGroup

const val GLOBAL_TAG = "Global_TAG"
const val FIRST_TAB_TITLE = "All Workouts"
const val NEW_GROUP = "New Group"
const val CHOOSE_GROUP = "Choose Group"

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
    if (num.toString().contains(".")){
        val decimals = num.toString().split(".").last()
        return if (decimals.length == 1 && decimals.first() == '0') num.toInt().toString()
        else num.toString()
    } else {
        return num.toString()
    }
}