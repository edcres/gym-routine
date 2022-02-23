package com.aldreduser.gymroutine.ui.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aldreduser.gymroutine.data.model.entities.WorkoutSet
import com.aldreduser.gymroutine.databinding.SetLinearLayouBinding

class SetsAdapter : ListAdapter<WorkoutSet, SetsAdapter.SetsViewHolder>(SetDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetsViewHolder {
        return SetsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holderWorkouts: SetsViewHolder, position: Int) =
        holderWorkouts.bind(getItem(position))

    class SetsViewHolder private constructor(
        private val binding: SetLinearLayouBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WorkoutSet) {
            binding.apply {
                // todo:
            }
        }

        companion object {
            fun from(parent: ViewGroup): SetsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SetLinearLayouBinding.inflate(layoutInflater, parent, false)
                return SetsViewHolder(binding)
            }
        }
    }
}

class SetDiffCallback : DiffUtil.ItemCallback<WorkoutSet>() {
    override fun areItemsTheSame(oldItem: WorkoutSet, newItem: WorkoutSet): Boolean {
        return oldItem.workoutPluSet == newItem.workoutPluSet
    }
    override fun areContentsTheSame(oldItem: WorkoutSet, newItem: WorkoutSet): Boolean {
        return oldItem == newItem
    }
}