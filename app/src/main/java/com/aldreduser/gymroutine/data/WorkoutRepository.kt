package com.aldreduser.gymroutine.data

import androidx.annotation.WorkerThread
import com.aldreduser.gymroutine.data.model.entities.Workout
import com.aldreduser.gymroutine.data.model.entities.WorkoutGroup
import com.aldreduser.gymroutine.data.model.entities.WorkoutSet
import com.aldreduser.gymroutine.data.model.room.WorkoutDao
import com.aldreduser.gymroutine.data.model.room.WorkoutGroupDao
import com.aldreduser.gymroutine.data.model.room.WorkoutSetDao
import com.aldreduser.gymroutine.data.model.room.WorkoutsRoomDatabase
import kotlinx.coroutines.flow.Flow

// Repo only has access to the DAOs, not the database.
class WorkoutRepository(
    private val mDatabase: WorkoutsRoomDatabase,
    private val mExecutors: AppExecutors
//    private val workoutGroupDao: WorkoutGroupDao,
//    private val workoutDao: WorkoutDao,
//    private val workoutSetDao: WorkoutSetDao)
{

    // todo: Add all the other DAOs with all their query functions. ()

    //Workout Group
    // getAlphabetizedWorkoutGroups
    val allWorkoutGroups: Flow<List<WorkoutGroup>> = workoutGroupDao.getAlphabetizedWorkoutGroups()
    // insert
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(workoutGroup: WorkoutGroup) {
        workoutGroupDao.insert(workoutGroup)
    }
    // delete (probably similar to insert)
    // workoutGroup+workout relationship (maybe, idk how this would work)

    //Workout
    // getAlphabetizedWorkouts
    val allWorkouts: Flow<List<Workout>> = workoutDao.getAlphabetizedWorkouts()
    // insert
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(workout: Workout) {
        workoutDao.insert(workout)
    }
    // update
    // delete
    // workout+workoutSet relationship (maybe, idk how this would work)

    //Workout Set
    // getAlphabetizedSets
    val allWorkoutSets: Flow<List<WorkoutSet>> = workoutSetDao.getAlphabetizedSets()
    // insert
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(workoutSet: WorkoutSet) {
        workoutSetDao.insert(workoutSet)
    }
    // update
    // delete

    // to instantiate the repository
    // (used to pass the repository as an argument to the viewmodel)
    companion object {

        private var sInstance: ToyRepository? = null

        fun getRepositoryInstance(database: WorkoutsRoomDatabase, executors: AppExecutors): WorkoutsRoomDatabase {
            return sInstance ?: synchronized(this) {
                sInstance
                    ?: WorkoutRepository(
                        database,
                        executors
                    ).also { sInstance = it }
            }
        }
    }
}