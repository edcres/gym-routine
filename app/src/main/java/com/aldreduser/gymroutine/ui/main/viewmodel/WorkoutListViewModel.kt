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
import kotlinx.coroutines.launch

// The ViewModel will transform the data from the Repository,
//  from Flow to LiveData and exposes the list of words as LiveData to the UI.
class WorkoutListViewModel : ViewModel() {

    private lateinit var roomDb: WorkoutsRoomDatabase
    private lateinit var repository: WorkoutsRepository
    var groupToDisplay: String = FIRST_TAB_TITLE
    val groupsOrdinals: MutableMap<String, Int> = mutableMapOf(FIRST_TAB_TITLE to 0)

    private val _groups = MutableLiveData<MutableList<WorkoutGroup>>()  // repository.allWorkoutGroups.asLiveData()
    val groups: LiveData<MutableList<WorkoutGroup>> get() = _groups
    private val _workouts = MutableLiveData<MutableList<Workout>>()  // repository.allWorkouts.asLiveData()
    val workouts: LiveData<MutableList<Workout>> get() = _workouts
    private val _sets = MutableLiveData<MutableList<WorkoutSet>>()  // repository.allWorkoutSets.asLiveData()
    val sets: LiveData<MutableList<WorkoutSet>> get() = _sets

    val groupNames: MutableList<String> = mutableListOf(FIRST_TAB_TITLE)
    private var tabWorkoutGroup = FIRST_TAB_TITLE

    // SETUP //
    fun startApplication(application: Application) {
        roomDb = WorkoutsRoomDatabase.getInstance(application)
        repository = WorkoutsRepository(roomDb)
    }
    // SETUP //


    // DATABASE QUERIES //
    // Workout Group //
    fun insertWorkoutGroup(workoutGroup: WorkoutGroup) = viewModelScope.launch {
        repository.insert(workoutGroup)
    }
    // Workout //
    fun insertWorkout(workout: Workout) = viewModelScope.launch {
        repository.insert(workout)
    }
    // Workout Set //
    fun insertWorkoutSet(workoutSet: WorkoutSet) = viewModelScope.launch {
        repository.insert(workoutSet)
    }
    // DATABASE QUERIES //

    // TABS //
    // todo: have an observer in startFragment for when a group is added/removed.
    //      and call this function from the view
    fun addTab(titleToAdd: String, groupTabsAdapter: GroupTabsAdapter) {
        val nextOrdinalId = groupsOrdinals.size - 1

        if(!groupNames.contains(titleToAdd)) {
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
