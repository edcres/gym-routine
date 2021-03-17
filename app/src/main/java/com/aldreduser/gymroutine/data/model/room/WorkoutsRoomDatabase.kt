package com.aldreduser.gymroutine.data.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aldreduser.gymroutine.data.model.entities.Workout
import com.aldreduser.gymroutine.data.model.entities.WorkoutGroup
import com.aldreduser.gymroutine.data.model.entities.WorkoutSet

// Creates the database: Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = arrayOf(
    WorkoutGroup::class,
    Workout::class,
    WorkoutSet::class),
    version = 1,
    exportSchema = false)
public abstract class WorkoutsRoomDatabase: RoomDatabase() {

    abstract fun workoutGroupDao(): WorkoutGroupDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun workoutSetDao(): WorkoutSetDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: WorkoutsRoomDatabase? = null

        fun getDatabase(context: Context): WorkoutsRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    WorkoutsRoomDatabase::class.java,
                    "workouts_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}