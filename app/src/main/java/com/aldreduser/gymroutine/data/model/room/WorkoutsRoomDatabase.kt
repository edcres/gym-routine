package com.aldreduser.gymroutine.data.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aldreduser.gymroutine.data.model.entities.Workout
import com.aldreduser.gymroutine.data.model.entities.WorkoutGroup
import com.aldreduser.gymroutine.data.model.entities.WorkoutSet

// Creates the database: Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = [WorkoutGroup::class, Workout::class, WorkoutSet::class],
    version = 1,
    exportSchema = false)
abstract class WorkoutsRoomDatabase: RoomDatabase() {

    abstract fun workoutGroupDao(): WorkoutGroupDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun workoutSetDao(): WorkoutSetDao

    companion object {
        @Volatile
        private var INSTANCE: WorkoutsRoomDatabase? = null
        private const val DATABASE_NAME = "workouts_database"

        fun getInstance(context: Context): WorkoutsRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    WorkoutsRoomDatabase::class.java,
                    DATABASE_NAME
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
