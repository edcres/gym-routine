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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private const val TAG = "ViewModel_TAG"

class WorkoutListViewModel : ViewModel() {

    private lateinit var roomDb: WorkoutsRoomDatabase
    private lateinit var repository: WorkoutsRepository
    var currentGroup: String = FIRST_TAB_TITLE
    val groupsOrdinals: MutableMap<String, Int> = mutableMapOf(FIRST_TAB_TITLE to 0)
    val groupNames: MutableList<String> = mutableListOf(FIRST_TAB_TITLE)

    private val _groups = MutableLiveData<MutableList<WorkoutGroup>>()
    val groups: LiveData<MutableList<WorkoutGroup>> get() = _groups
    private val _workouts = MutableLiveData<MutableList<Workout>>()
    val workouts: LiveData<MutableList<Workout>> get() = _workouts
    private val _sets = MutableLiveData<MutableList<WorkoutSet>>()
    val sets: LiveData<MutableList<WorkoutSet>> get() = _sets

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

    // DATABASE QUERIES //
    private fun collectAllWorkouts() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.allWorkoutGroups.collect {
                _groups.postValue(it.toMutableList())
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            repository.allWorkouts.collect {
                _workouts.postValue(it.toMutableList())
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            repository.allWorkoutSets.collect {
                _sets.postValue(it.toMutableList())
            }
        }
    }
    fun insertWorkoutGroup(workoutGroup: WorkoutGroup, workoutId: Long?) = CoroutineScope(Dispatchers.IO).launch {
        repository.insertGroup(workoutGroup, workoutId)
    }
    fun insertWorkout(workout: Workout): LiveData<Long> {
        val itemId = MutableLiveData<Long>()
        CoroutineScope(Dispatchers.IO).launch {
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
    fun insertWorkoutSet(workoutSet: WorkoutSet) = CoroutineScope(Dispatchers.IO).launch  {
        repository.insert(workoutSet)
    }
    fun updateGroupOnWorkout(workoutId: Long, groupSelected: String) =
        CoroutineScope(Dispatchers.IO).launch {
            val oldGroupName = repository.getGroupOfWorkout(workoutId)
            repository.updateWorkout(workoutId, groupSelected)
            checkIfDeleteGroup(oldGroupName)
        }
    fun updateWorkoutName(workout: Workout) = CoroutineScope(Dispatchers.IO).launch {
        repository.update(workout)
        repository.updateWorkoutOnSets(workout.id, workout.workoutName)
    }
    fun updateSet(set: WorkoutSet) = CoroutineScope(Dispatchers.IO).launch {
        repository.update(set)
    }
    fun removeWorkout(workout: Workout, groupName: String) = CoroutineScope(Dispatchers.IO).launch {
        repository.deleteWorkout(workout)
        checkIfDeleteGroup(groupName)
    }
    fun removeSet(set: WorkoutSet) = CoroutineScope(Dispatchers.IO).launch {
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
        CoroutineScope(Dispatchers.IO).launch {
            allWorkouts.postValue(repository.getAllWorkouts())
        }
        return allWorkouts
    }
    fun getWorkoutsOfGroup(group: String): LiveData<List<Workout>> {
//        val workoutsOfGroup = MutableLiveData<List<Workout>>()
//        CoroutineScope(Dispatchers.IO).launch {
            return repository.getWorkoutsOfThisGroup(group)
//            workoutsOfGroup.postValue(repository.getWorkoutsOfThisGroup(group))
//        }
//        return workoutsOfGroup
    }
    fun getSetsOfWorkout(workoutId: Long): LiveData<List<WorkoutSet>> {
        val setsOfWorkout = MutableLiveData<List<WorkoutSet>>()
        CoroutineScope(Dispatchers.IO).launch {
            setsOfWorkout.postValue(repository.getSetsOfWorkout(workoutId))
        }
        return setsOfWorkout
    }
    fun getWorkoutName(workoutId: Long): LiveData<String> {
        val workoutName = MutableLiveData<String>()
        CoroutineScope(Dispatchers.IO).launch {
            workoutName.postValue(repository.getWorkoutName(workoutId))
        }
        return workoutName
    }
    fun getLastSet(workoutId: Long): LiveData<WorkoutSet> {
        val lastSet = MutableLiveData<WorkoutSet>()
        CoroutineScope(Dispatchers.IO).launch {
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
