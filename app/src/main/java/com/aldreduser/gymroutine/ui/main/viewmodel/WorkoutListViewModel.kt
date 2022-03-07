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

// The ViewModel will transform the data from the Repository,
//  from Flow to LiveData and exposes the list of words as LiveData to the UI.
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

    // Used to update the sets that were edited
    var currentWorkoutName: String? = null

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
    fun insertWorkoutGroup(workoutGroup: WorkoutGroup) = viewModelScope.launch {
        repository.insert(workoutGroup)
    }
    fun insertWorkout(workout: Workout) = viewModelScope.launch {
        repository.insert(workout)
    }
    fun insertWorkoutSet(workoutSet: WorkoutSet) = viewModelScope.launch {
        repository.insert(workoutSet)
    }
    fun updateTitle(workout: Workout) {
        // todo:
        // update workout Workout entity and  WorkoutSet entity
    }
    fun updateRep(set: WorkoutSet) {
        // todo:
    }
    fun updateWeight(set: WorkoutSet) {
        // todo:
    }
    fun removeSet(set: WorkoutSet) {
        // todo:
    }
    fun getWorkoutsOfThisGroup(group: WorkoutGroup): List<Workout> {
        // todo: do a query that gets all the workouts that are part of the group
        //      group = 'groupToDisplay'
        return mutableListOf()
    }
    fun getSetsOfThisWorkout(workoutName: String): List<WorkoutSet> {
        // todo
        return mutableListOf()
    }
    fun getNextSetNum(workoutName: String): Int {
        // todo: do a query that gets the next set in that workout
        //  check how many sets are part of that workout
        return 2
    }
    fun addGroupToWorkout(workout: Workout) {
        // todo: add the 'groupSelected' to the workout_group in Workout Entity in the database
        //    (watch out for concurrency issues)
    }
    fun groupHasWorkouts(group: WorkoutGroup): Boolean {
        // todo: call this function
        // todo: check if this group has any workouts
        // if not then delete group
        return true
    }
    // DATABASE QUERIES //

    // TABS //
    fun addTab(titlesToAdd: List<String>, groupTabsAdapter: GroupTabsAdapter) {
        titlesToAdd.forEach { title ->
            if (!groupNames.contains(title)) {
                val nextOrdinalId = groupsOrdinals.size - 1
                groupTabsAdapter.addTab(nextOrdinalId + 1, title)
            } else {
                Log.e(GLOBAL_TAG, "\t\ttitles contains next title \t\t $groupNames\n$title")
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
