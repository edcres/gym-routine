package com.aldreduser.gymroutine.ui.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aldreduser.gymroutine.data.model.entities.WorkoutSet
import com.aldreduser.gymroutine.databinding.SetLinearLayouBinding
import com.aldreduser.gymroutine.ui.main.viewmodel.WorkoutListViewModel

// This adapter will be used in 2 places: WorkoutListAdapter, EditWorkoutFragment
class SetsAdapter(
    private val viewModel: WorkoutListViewModel,
    private val setAreRemoved: Boolean
) : ListAdapter<WorkoutSet, SetsAdapter.SetsViewHolder>(SetDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetsViewHolder {
        return SetsViewHolder.from(viewModel, setAreRemoved, parent)
    }

    override fun onBindViewHolder(holderWorkouts: SetsViewHolder, position: Int) =
        holderWorkouts.bind(getItem(position))

    class SetsViewHolder private constructor(
        private val viewModel: WorkoutListViewModel,
        private val setAreRemoved: Boolean,
        private val binding: SetLinearLayouBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WorkoutSet) {
            binding.apply {
                if(setAreRemoved) {
                    removeSetBtn.visibility = View.VISIBLE
                    spacer.visibility = View.VISIBLE
                    removeSetBtn.setOnClickListener {
                        viewModel.currentWorkoutName = item.workoutName
                        viewModel.removeSet(item)
                    }
                }

                setText.setText(item.set.toString())
                repsText.setText(item.reps.toString())
                weightText.setText(item.weight.toString())

                repsText.doAfterTextChanged {
                    viewModel.currentWorkoutName = item.workoutName
                    item.reps = it.toString().toInt()
                    viewModel.updateSet(item)
                }
                weightText.doAfterTextChanged {
                    viewModel.currentWorkoutName = item.workoutName
                    item.weight = it.toString().toDouble()
                    viewModel.updateSet(item)
                }
            }
        }

        companion object {
            fun from(
                viewModel: WorkoutListViewModel,
                setAreRemoved: Boolean,
                parent: ViewGroup
            ): SetsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SetLinearLayouBinding
                    .inflate(layoutInflater, parent, false)
                return SetsViewHolder(viewModel, setAreRemoved, binding)
            }
        }
    }
}

class SetDiffCallback : DiffUtil.ItemCallback<WorkoutSet>() {
    override fun areItemsTheSame(oldItem: WorkoutSet, newItem: WorkoutSet): Boolean {
        return oldItem.workoutPlusSet == newItem.workoutPlusSet
    }
    override fun areContentsTheSame(oldItem: WorkoutSet, newItem: WorkoutSet): Boolean {
        return oldItem == newItem
    }
}