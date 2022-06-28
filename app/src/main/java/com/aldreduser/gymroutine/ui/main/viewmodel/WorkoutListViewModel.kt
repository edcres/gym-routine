package com.aldreduser.gymroutine.ui.main.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.aldreduser.gymroutine.data.WorkoutsRepository
import com.aldreduser.gymroutine.data.model.entities.Workout
import com.aldreduser.gymroutine.data.model.entities.WorkoutGroup
import com.aldreduser.gymroutine.data.model.entities.WorkoutSet
import com.aldreduser.gymroutine.data.model.room.WorkoutsRoomDatabase
import com.aldreduser.gymroutine.ui.main.adapters.GroupTabsAdapter
import com.aldreduser.gymroutine.utils.FIRST_TAB_TITLE
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private const val TAG = "ViewModel__TAG"

class WorkoutListViewModel : ViewModel() {

    private lateinit var roomDb: WorkoutsRoomDatabase
    private lateinit var repository: WorkoutsRepository
    var currentGroup: String = FIRST_TAB_TITLE
    val groupsOrdinals: MutableMap<String, Int> = mutableMapOf(FIRST_TAB_TITLE to 0)
    val groupNames: MutableList<String> = mutableListOf(FIRST_TAB_TITLE)

    private val _groups = MutableLiveData<List<WorkoutGroup>>()
    val groups: LiveData<List<WorkoutGroup>> get() = _groups
    private val _workouts = MutableLiveData<List<Workout>>()
    val workouts: LiveData<List<Workout>> get() = _workouts
    private val _sets = MutableLiveData<List<WorkoutSet>>()
    val sets: LiveData<List<WorkoutSet>> get() = _sets

    private var _menuEditIsOn = MutableLiveData(false)
    val menuEditIsOn: LiveData<Boolean> get() = _menuEditIsOn
    private var _itemToEdit = MutableLiveData<Any?>()
    val itemToEdit: LiveData<Any?> get() = _itemToEdit
    private var _hiddenTxt = MutableLiveData(false)
    val hiddenTxt: LiveData<Boolean> get() = _hiddenTxt

    // Used to update the sets that were edited
    var workoutIdToEdit: Long? = null
    var editWorkoutSetsPreviousSize: Int = 0

    // HELPERS //
    fun toggleEditBtn(): Boolean {
        val newValue = !_menuEditIsOn.value!!
        _menuEditIsOn.value = newValue
        return newValue
    }
    fun turnOffEditMode(): Boolean {
        _menuEditIsOn.postValue(false)
        return false
    }
    fun toggleHiddenTxt() {
        // This is a work around a bug. The purpose is for the recyclerview to get resized.
        _hiddenTxt.value = !_hiddenTxt.value!!
    }
    fun setItemToEdit(chosenItem: Any?) {
        _itemToEdit.value = chosenItem
    }
    private suspend fun checkIfDeleteGroup(groupName: String) {
        if(repository.groupHasWorkouts(groupName)) {
            Log.i(TAG, "Group $groupName still has workouts.")
        } else {
            if (groupName != FIRST_TAB_TITLE) {
                repository.deleteGroup(groupName)
                Log.i(TAG, "Group $groupName removed.")
            }
        }
    }
    // HELPERS //

    // SETUP //
    fun startApplication(application: Application) {
        roomDb = WorkoutsRoomDatabase.getInstance(application)
        repository = WorkoutsRepository(roomDb)
        collectAllWorkouts()
        insertWorkoutGroup(WorkoutGroup(FIRST_TAB_TITLE), null)
    }
    // SETUP //

    private fun fillGroups(): List<WorkoutGroup> {
        return listOf(WorkoutGroup(groupName = "test"))
    }
    private fun fillWorkouts(): List<Workout> {
        val workoutsList = mutableListOf<Workout>()
        for (i in 0 until 30) {
            workoutsList.add(
                Workout(
                    id = i.toLong(),
                    workoutName = i.toString(),
                    workoutGroup = "test",
                )
            )
        }
        return workoutsList
    }
    fun fillSets(workoutNum: Int): List<WorkoutSet> {
        val setsList = mutableListOf<WorkoutSet>()
        for (j in 0 until 1) {
            setsList.add(
                WorkoutSet(
                    id = "$workoutNum$j".toLong(),
                    workoutId = workoutNum.toLong(),
                    workoutName = workoutNum.toString(),
                    set = j,
                    45,
                    34.0
                )
            )
        }
        return setsList
    }

    // DATABASE QUERIES //
    private fun collectAllWorkouts() {
        viewModelScope.launch {
//            repository.allWorkoutGroups.collect {
//                _groups.postValue(it)
//            }
            _groups.postValue(fillGroups())
        }
        viewModelScope.launch {
//            repository.allWorkouts.collect {
//                _workouts.postValue(it)
//            }
            _workouts.postValue(fillWorkouts())
        }
        viewModelScope.launch {
//            repository.allWorkoutSets.collect {
//                _sets.postValue(it)
//            }
//            _sets.postValue(fillSets())
        }
    }
    fun insertWorkoutGroup(workoutGroup: WorkoutGroup, workoutId: Long?) = viewModelScope.launch {
        repository.insertGroup(workoutGroup, workoutId)
    }
    fun insertWorkout(workout: Workout): LiveData<Long> {
        val itemId = MutableLiveData<Long>()
        viewModelScope.launch {
            val workoutId = repository.insert(workout)
            itemId.postValue(workoutId)
            repository.insert(
                WorkoutSet(
                    workoutId = workoutId,
                    workoutName = workout.workoutName,
                    set = 1,
                    reps = 0,
                    weight = 0.0
                )
            )
        }
        return itemId
    }
    fun insertWorkoutSet(workoutSet: WorkoutSet) = viewModelScope.launch  {
        repository.insert(workoutSet)
    }
    fun updateGroupOnWorkout(workoutId: Long, groupSelected: String) =
        viewModelScope.launch {
            val oldGroupName = repository.getGroupOfWorkout(workoutId)
            repository.updateWorkout(workoutId, groupSelected)
            checkIfDeleteGroup(oldGroupName)
        }
    fun updateWorkoutNotes(workoutId: Long, muscles: String, notes: String) =
        viewModelScope.launch {
            repository.updateWorkoutNotes(workoutId, muscles, notes)
    }
    fun updateWorkoutName(workout: Workout) = viewModelScope.launch {
        repository.update(workout)
        repository.updateWorkoutOnSets(workout.id, workout.workoutName)
    }
    fun updateSet(set: WorkoutSet) = viewModelScope.launch {
        repository.update(set)
    }
    fun removeWorkout(workout: Workout, groupName: String) = viewModelScope.launch {
        repository.deleteWorkout(workout)
        checkIfDeleteGroup(groupName)
    }
    fun removeSet(set: WorkoutSet) = viewModelScope.launch {
        if (sets.value != null) {
            repository.updateSetOnSets(
                set.workoutId,
                set.set
            )
        }
        repository.deleteSet(set)
    }
    fun getAllWorkouts(): LiveData<List<Workout>> {
        val allWorkouts = MutableLiveData<List<Workout>>()
        viewModelScope.launch {
            allWorkouts.postValue(repository.getAllWorkouts())
        }
        return allWorkouts
    }
    fun getWorkoutWithId(id: Long): LiveData<Workout> {
        val workout = MutableLiveData<Workout>()
        viewModelScope.launch {
            workout.postValue(repository.getWorkoutWithId(id))
        }
        return workout
    }
    fun getWorkoutsOfGroup(group: String): LiveData<List<Workout>> {
        val workoutsOfGroup = MutableLiveData<List<Workout>>()
        viewModelScope.launch {
            workoutsOfGroup.postValue(repository.getWorkoutsOfThisGroup(group))
        }
        return workoutsOfGroup
    }
    fun getSetsOfWorkout(workoutId: Long): LiveData<List<WorkoutSet>> {
        val setsOfWorkout = MutableLiveData<List<WorkoutSet>>()
        viewModelScope.launch {
            setsOfWorkout.postValue(repository.getSetsOfWorkout(workoutId))
        }
        return setsOfWorkout
    }
    fun getWorkoutName(workoutId: Long): LiveData<String> {
        val workoutName = MutableLiveData<String>()
        viewModelScope.launch {
            workoutName.postValue(repository.getWorkoutName(workoutId))
        }
        return workoutName
    }
    fun getLastSet(workoutId: Long): LiveData<WorkoutSet> {
        val lastSet = MutableLiveData<WorkoutSet>()
        viewModelScope.launch {
            lastSet.postValue(repository.getLastSet(workoutId))
        }
        return lastSet
    }
    // DATABASE QUERIES //

    // TABS //
    fun addTab(titlesToAdd: List<String>, groupTabsAdapter: GroupTabsAdapter) {
        titlesToAdd.forEach { title ->
            if (!groupNames.contains(title)) {
                val nextOrdinalId = groupsOrdinals.size - 1
                groupTabsAdapter.addTab(nextOrdinalId + 1, title)
            } else {
                Log.e(TAG, "\t\ttitles contains next title \t\t $groupNames\n$title")
            }
        }
    }
    fun removeTab(titleToRemove: String, groupTabsAdapter: GroupTabsAdapter) {
        val numOfTabs = groupNames.size
        if (numOfTabs > 1 && titleToRemove != FIRST_TAB_TITLE) {
            groupTabsAdapter.removeTab(groupsOrdinals[titleToRemove]!!, titleToRemove)
        }
    }
    // TABS //
}
