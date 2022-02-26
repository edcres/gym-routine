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

    private val _groups = MutableLiveData<MutableList<WorkoutGroup>>()  // repository.allWorkoutGroups.asLiveData()
    val groups: LiveData<MutableList<WorkoutGroup>> get() = _groups
    private val _workouts = MutableLiveData<MutableList<Workout>>()  // repository.allWorkouts.asLiveData()
    val workouts: LiveData<MutableList<Workout>> get() = _workouts
    private val _sets = MutableLiveData<MutableList<WorkoutSet>>()  // repository.allWorkoutSets.asLiveData()
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

    // todo: I don't know if the workout variables in the repo are updated automatically.
    //      test that in the test app
    //      if they are then i don't need to update the livedata variables from here
    // DATABASE QUERIES //
    fun fetchAllWorkouts() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.allWorkoutGroups.collect {
                _groups.postValue(it.toMutableList())
            }
            repository.allWorkouts.collect {
                _workouts.postValue(it.toMutableList())
            }
            repository.allWorkoutSets.collect {
                _sets.postValue(it.toMutableList())
            }
        }
    }
    // Workout Group //
    fun insertWorkoutGroup(workoutGroup: WorkoutGroup) = viewModelScope.launch {
        _groups.value!!.add(workoutGroup)
        repository.insert(workoutGroup)
    }
    // Workout //
    fun insertWorkout(workout: Workout) = viewModelScope.launch {
        _workouts.value!!.add(workout)
        repository.insert(workout)
    }
    // Workout Set //
    fun insertWorkoutSet(workoutSet: WorkoutSet) = viewModelScope.launch {
        _sets.value!!.add(workoutSet)
        repository.insert(workoutSet)
    }

    fun updateTitle(workout: Workout) {
        // todo:
        // update workout Workout entity and  WorkoutSet entity
    }
    // todo: consider turning these 2 into one function
    fun updateRep(set: WorkoutSet) {
        // todo:
    }
    fun updateWeight(set: WorkoutSet) {
        // todo:
    }
    fun removeSet(set: WorkoutSet) {
        // todo:
    }
    fun getSetsOfThisWorkout(workoutName: String): List<WorkoutSet> {
        // todo
        return mutableListOf()
    }
    fun addGroupToWorkout(workout: Workout) {
        // todo: add the 'groupSelected' to the workout_group in Workout Entity in the database
        //    (watch out for concurrency issues)
    }
    // DATABASE QUERIES //

    // TABS //
    fun addTab(titleToAdd: String, groupTabsAdapter: GroupTabsAdapter) {
        if(!groupNames.contains(titleToAdd)) {
            val nextOrdinalId = groupsOrdinals.size - 1
            groupTabsAdapter.addTab(nextOrdinalId+1, titleToAdd)
        } else {
            Log.e(GLOBAL_TAG, "\t\ttitles contains next title " +
                    "\t\t $groupNames\n$titleToAdd")
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
