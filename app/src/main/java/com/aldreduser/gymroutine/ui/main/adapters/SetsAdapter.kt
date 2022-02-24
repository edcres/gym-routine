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
        val viewModel: WorkoutListViewModel,
        private val setAreRemoved: Boolean,
        private val binding: SetLinearLayouBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WorkoutSet) {
            binding.apply {
                // todo: choose whether these should be gone or visible
                if(setAreRemoved) {
                    removeSetBtn.visibility = View.VISIBLE
                    spacer.visibility = View.VISIBLE
                    removeSetBtn.setOnClickListener {
                        // todo: remove the set from the db
                    }
                }

                setText.setText(item.set.toString())
                repsText.setText(item.reps.toString())
                weightText.setText(item.weight.toString())

                // todo: when user types in a text box, send the data to the db
                repsText.doAfterTextChanged {
                }
                weightText.doAfterTextChanged {
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