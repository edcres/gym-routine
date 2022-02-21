package com.aldreduser.gymroutine.ui.main.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aldreduser.gymroutine.data.model.entities.Workout
import com.aldreduser.gymroutine.databinding.WorkoutItemBinding
import com.aldreduser.gymroutine.ui.main.viewmodel.WorkoutListViewModel
import com.aldreduser.gymroutine.utils.GLOBAL_TAG
import com.aldreduser.gymroutine.utils.NEW_GROUP

class WorkoutListAdapter(
    private val workoutsViewModel: WorkoutListViewModel,
    private val context: Context,
    private val fragLifecycleOwner: LifecycleOwner
) :
    ListAdapter<Workout, WorkoutListAdapter.WorkoutsViewHolder>(WorkoutDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutsViewHolder {
        return WorkoutsViewHolder.from(workoutsViewModel, context, fragLifecycleOwner, parent)
    }

    override fun onBindViewHolder(holderWorkouts: WorkoutsViewHolder, position: Int) =
        holderWorkouts.bind(getItem(position))

    class WorkoutsViewHolder private constructor(
        val workoutsViewModel: WorkoutListViewModel,
        val context: Context,
        private val fragLifecycleOwner: LifecycleOwner,
        val binding: WorkoutItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Workout) {
            binding.apply {

                // choose whether the spinner shows up or not
                val spinnerList = workoutsViewModel.groupNames
                spinnerList.add(NEW_GROUP)
                chooseGroupBtn.adapter = ArrayAdapter(
                    context,
                    android.R.layout.simple_list_item_1,
                    spinnerList
                )
                chooseGroupBtn.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val groupName = workoutsViewModel.groupNames[position]
                        if(groupName == NEW_GROUP) {
                            // todo: hide the spinner and show an editText for user to type the new group name

                            // todo: when user click on editText
                            workoutsViewModel.addNewGroup(newName)
                            // todo: also add the workout to the group name in the database
                            //    (watch out for concurrency issues)
                        } else {
                            // todo: add the workout to the group name in the database
                            //    (watch out for concurrency issues)
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        Log.i(GLOBAL_TAG, "Nothing was clicked.")
                    }
                }

                binding.executePendingBindings()
            }
        }

        companion object {
            fun from(
                workoutsViewModel: WorkoutListViewModel,
                context: Context,
                fragLifecycleOwner: LifecycleOwner,
                parent: ViewGroup
            ): WorkoutsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = WorkoutItemBinding.inflate(layoutInflater, parent, false)
                return WorkoutsViewHolder(workoutsViewModel, context, fragLifecycleOwner, binding)
            }
        }
    }
}

class WorkoutDiffCallback : DiffUtil.ItemCallback<Workout>() {
    override fun areItemsTheSame(oldItem: Workout, newItem: Workout): Boolean {
        return oldItem.thisWorkoutName == newItem.thisWorkoutName
    }
    override fun areContentsTheSame(oldItem: Workout, newItem: Workout): Boolean {
        return oldItem == newItem
    }
}