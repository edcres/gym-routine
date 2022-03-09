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

// The ViewModel will transform the data from the Repository,
//  from Flow to LiveData and exposes the list of words as LiveData to the UI.
class WorkoutListViewModel : ViewModel() {

    private val tag = "ViewModel TAG"
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

    // Used to update the sets that were edited
    var workoutIdToEdit: Long? = null

    // HELPERS //
    fun toggleEditBtn() {
        _menuEditIsOn.value = !_menuEditIsOn.value!!
    }
    fun setItemToEdit(chosenItem: Any?) {
        _itemToEdit.value = chosenItem
    }
    // HELPERS //

    // SETUP //
    fun startApplication(application: Application) {
        roomDb = WorkoutsRoomDatabase.getInstance(application)
        repository = WorkoutsRepository(roomDb)
        fetchAllWorkouts()
    }
    // SETUP //

    // DATABASE QUERIES //
    private fun fetchAllWorkouts() {
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
    fun insertWorkoutGroup(workoutGroup: WorkoutGroup) = CoroutineScope(Dispatchers.IO).launch {
        repository.insert(workoutGroup)
    }
    fun insertWorkout(workout: Workout): MutableLiveData<Long> {
        val itemId = MutableLiveData<Long>()
        CoroutineScope(Dispatchers.IO).launch {
            itemId.postValue(repository.insert(workout))
        }
        return itemId
    }
    fun insertWorkoutSet(workoutSet: WorkoutSet) = CoroutineScope(Dispatchers.IO).launch  {
        repository.insert(workoutSet)
    }
    fun updateGroupOnWorkout(workout: Workout) = CoroutineScope(Dispatchers.IO).launch {
        repository.update(workout)
    }
    fun updateGroupOnWorkout(workoutId: Long, groupSelected: String) = CoroutineScope(Dispatchers.IO).launch {
        repository.updateWorkout(workoutId, groupSelected)
    }
    fun updateWorkoutName(workout: Workout) = CoroutineScope(Dispatchers.IO).launch  {
        repository.update(workout)
        repository.updateWorkoutOnSets(workout.id, workout.workoutName)
    }
    fun updateSet(set: WorkoutSet) = CoroutineScope(Dispatchers.IO).launch {
        repository.update(set)
    }
    fun possiblyRemoveGroup(group: WorkoutGroup) {
        // todo: Call this function
        // If the group has no workouts, remove it from db.
        CoroutineScope(Dispatchers.IO).launch {
            if(repository.groupHasWorkouts(group.groupName)) {
                Log.i(tag, "Group ${group.groupName} still has workouts.")
            } else {
                repository.deleteGroup(group)
                Log.i(tag, "Group ${group.groupName} removed.")
            }
        }
    }
    fun removeWorkout(workout: Workout) = CoroutineScope(Dispatchers.IO).launch {
        // todo: call this function
        repository.deleteWorkout(workout)
    }
    fun removeSet(set: WorkoutSet) = CoroutineScope(Dispatchers.IO).launch {
        repository.deleteSet(set)
        if (sets.value != null) repository.updateSetOnSets(
            set.set,
            repository.getSetsOfWorkout(set.workoutId)
        )
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
//    fun getNextSetNum(workoutId: Long): MutableLiveData<Int> {
//        // todo: Probably get rid of this
//        val nextNum = MutableLiveData<Int>()
//        CoroutineScope(Dispatchers.IO).launch {
//            nextNum.postValue(repository.getNextSetNum(workoutId) + 1)
//        }
//        return nextNum
//    }
//    fun getWorkout(workoutId: Long): MutableLiveData<Workout> {
//        val thisWorkout = MutableLiveData<Workout>()
//        CoroutineScope(Dispatchers.IO).launch {
//            thisWorkout.postValue(repository.getWorkout(workoutId))
//        }
//        return thisWorkout
//    }
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
