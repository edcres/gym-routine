package com.aldreduser.gymroutine.ui.main

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.aldreduser.gymroutine.R

/** App explanation:
 *
 * App displays a list of workout, one tab per workout group
 * A workout has sets, reps and weight. Under a Workout name
 * The user can add more workouts, add it to or create a group.
 * To edit a workout, user can click on the edit button or edit the workout directly from the list.
 */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("main__TAG", "onCreate: called\nbundle = $savedInstanceState")

        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.black)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("main__TAG", "onDestroy: called")
    }
}
