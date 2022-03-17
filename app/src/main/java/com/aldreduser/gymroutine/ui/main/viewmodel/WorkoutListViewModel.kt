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
import com.aldreduser.gymroutine.utils.GLOBAL_TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class WorkoutListViewModel : ViewModel() {

    private val tag = "ViewModel_TAG"
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

    // HELPERS //
    fun toggleEditBtn() {
        _menuEditIsOn.value = !_menuEditIsOn.value!!
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
            Log.i(tag, "Group $groupName still has workouts.")
        } else {
            if (groupName != FIRST_TAB_TITLE) {
                repository.deleteGroup(groupName)
                Log.i(tag, "Group $groupName removed.")
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
    fun insertWorkout(workout: Workout): MutableLiveData<Long> {
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
        Log.d(GLOBAL_TAG, "updateSet: set sent to update")
        Log.d(GLOBAL_TAG, "set = $set")
        repository.update(set)
    }
    fun removeWorkout(workout: Workout, groupName: String) = CoroutineScope(Dispatchers.IO).launch {
        repository.deleteWorkout(workout)
        checkIfDeleteGroup(groupName)
    }
    fun removeSet(set: WorkoutSet) = CoroutineScope(Dispatchers.IO).launch {
        repository.deleteSet(set)
        if (sets.value != null) repository.updateSetOnSets(
            set.workoutId,
            set.set,
            repository.getSetsOfWorkout(set.workoutId)
        )
    }
    fun getAllWorkouts(): MutableLiveData<List<Workout>> {
        val allWorkouts = MutableLiveData<List<Workout>>()
        CoroutineScope(Dispatchers.IO).launch {
            allWorkouts.postValue(repository.getAllWorkouts())
        }
        return allWorkouts
    }
    fun getWorkoutsOfGroup(group: String): MutableLiveData<List<Workout>> {
        val workoutsOfGroup = MutableLiveData<List<Workout>>()
        CoroutineScope(Dispatchers.IO).launch {
            workoutsOfGroup.postValue(repository.getWorkoutsOfThisGroup(group))
        }
        return workoutsOfGroup
    }
    fun getSetsOfWorkout(workoutId: Long): MutableLiveData<List<WorkoutSet>> {
        val setsOfWorkout = MutableLiveData<List<WorkoutSet>>()
        CoroutineScope(Dispatchers.IO).launch {
            setsOfWorkout.postValue(repository.getSetsOfWorkout(workoutId))
        }
        return setsOfWorkout
    }
    fun getWorkoutName(workoutId: Long): MutableLiveData<String> {
        val workoutName = MutableLiveData<String>()
        CoroutineScope(Dispatchers.IO).launch {
            workoutName.postValue(repository.getWorkoutName(workoutId))
        }
        return workoutName
    }
    fun getLastSet(workoutId: Long): MutableLiveData<WorkoutSet> {
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
                Log.e(tag, "\t\ttitles contains next title \t\t $groupNames\n$title")
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
