package com.aldreduser.gymroutine.ui.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aldreduser.gymroutine.data.model.entities.Workout
import com.aldreduser.gymroutine.databinding.WorkoutsRecyclerItemBinding

// todo: bind all other entities, not just 'Workout' entity

class WorkoutCategoryListAdapter() :
    ListAdapter<Workout, WorkoutCategoryListAdapter.ViewHolder>(WorkoutDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position)!!)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: WorkoutsRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Workout) {
            binding.workoutEntity = item
            binding.executePendingBindings()    // idk what this is for
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = WorkoutsRecyclerItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}
