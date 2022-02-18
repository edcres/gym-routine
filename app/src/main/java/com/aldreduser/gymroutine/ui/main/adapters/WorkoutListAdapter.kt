package com.aldreduser.gymroutine.ui.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aldreduser.gymroutine.data.model.entities.Workout
import com.aldreduser.gymroutine.databinding.WorkoutItemBinding
import com.aldreduser.gymroutine.ui.main.viewmodel.WorkoutListViewModel

class WorkoutListAdapter(
    private val workoutsViewModel: WorkoutListViewModel,
    private val fragLifecycleOwner: LifecycleOwner
) :
    ListAdapter<Workout, WorkoutListAdapter.WorkoutsViewHolder>(WorkoutDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutsViewHolder {
        return WorkoutsViewHolder.from(workoutsViewModel, fragLifecycleOwner, parent)
    }

    override fun onBindViewHolder(holderWorkouts: WorkoutsViewHolder, position: Int) =
        holderWorkouts.bind(getItem(position))

    class WorkoutsViewHolder private constructor(
        val workoutsViewModel: WorkoutListViewModel,
        private val fragLifecycleOwner: LifecycleOwner,
        val binding: WorkoutItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Workout) {
            binding.apply {

                binding.executePendingBindings()
            }
        }

        companion object {
            fun from(
                workoutsViewModel: WorkoutListViewModel,
                fragLifecycleOwner: LifecycleOwner,
                parent: ViewGroup
            ): WorkoutsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = WorkoutItemBinding.inflate(layoutInflater, parent, false)
                return WorkoutsViewHolder(workoutsViewModel, fragLifecycleOwner, binding)
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