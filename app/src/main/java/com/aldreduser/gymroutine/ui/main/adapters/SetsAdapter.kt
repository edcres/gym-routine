package com.aldreduser.gymroutine.ui.main.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aldreduser.gymroutine.data.model.entities.WorkoutSet
import com.aldreduser.gymroutine.databinding.SetLinearLayoutBinding
import com.aldreduser.gymroutine.ui.main.viewmodel.WorkoutListViewModel
import com.aldreduser.gymroutine.utils.GLOBAL_TAG
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
        private val binding: SetLinearLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(workoutSet: WorkoutSet) {
            binding.apply {
                if(setAreRemoved) {
                    // If sets can be removed
                    removeSetBtn.visibility = View.VISIBLE
                    spacer.visibility = View.VISIBLE
                    removeSetBtn.setOnClickListener {
                        viewModel.workoutIdToEdit = workoutSet.workoutId
                        viewModel.removeSet(workoutSet)
                    }
                }

                setText.setText(workoutSet.set.toString())
                repsText.setText(workoutSet.reps.toString())
                weightText.setText(workoutSet.weight.toString())

                repsText.doAfterTextChanged {
                    viewModel.workoutIdToEdit = workoutSet.workoutId
                    workoutSet.reps = it.toString().toInt()
                    viewModel.updateSet(workoutSet)
                }
                weightText.doAfterTextChanged {
                    viewModel.workoutIdToEdit = workoutSet.workoutId
                    workoutSet.weight = it.toString().toDouble()
                    viewModel.updateSet(workoutSet)
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
                val binding = SetLinearLayoutBinding
                    .inflate(layoutInflater, parent, false)
                return SetsViewHolder(viewModel, setAreRemoved, binding)
            }
        }
    }
}

class SetDiffCallback : DiffUtil.ItemCallback<WorkoutSet>() {
    override fun areItemsTheSame(oldItem: WorkoutSet, newItem: WorkoutSet): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: WorkoutSet, newItem: WorkoutSet): Boolean {
        return oldItem == newItem
    }
}