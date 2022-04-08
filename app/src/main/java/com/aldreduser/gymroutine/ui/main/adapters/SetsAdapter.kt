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

private const val TAG = "Sets_TAG"

// This adapter will be used in 2 places: WorkoutListAdapter, EditWorkoutFragment
class SetsAdapter(
    private val viewModel: WorkoutListViewModel,
    private val setAreRemoved: Boolean
) : ListAdapter<WorkoutSet, SetsAdapter.SetsViewHolder>(SetDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SetsViewHolder.from(viewModel, setAreRemoved, parent)

    override fun onBindViewHolder(holderWorkouts: SetsViewHolder, position: Int) =
        holderWorkouts.bind(getItem(position))

    class SetsViewHolder private constructor(
        private val viewModel: WorkoutListViewModel,
        private val setAreRemoved: Boolean,
        private val binding: SetLinearLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(workoutSet: WorkoutSet) {
            binding.apply {
                ifSetsAreRemoved(workoutSet)

                val startingRepsTxt = workoutSet.reps.toString()
                val startingWeightTxt = workoutSet.weight.toString()

//                Log.d(TAG, "set info:\nset: ${workoutSet.set}\nweight: ${workoutSet.weight}\nreps: ${workoutSet.reps}")

                setText.setText(workoutSet.set.toString())

                if (repsText.text.isEmpty() && weightText.text.isEmpty()) {
                    repsText.setText(startingRepsTxt)
                    weightText.setText(startingWeightTxt)
                }

                weightText.doAfterTextChanged {
                    if(it.toString().isNotEmpty() && it.toString() != startingWeightTxt) {
                        viewModel.workoutIdToEdit = workoutSet.workoutId
                        workoutSet.weight = it.toString().toDouble()
                        viewModel.updateSet(workoutSet)
                    }
                }

                repsText.doAfterTextChanged {
                    if(it.toString().isNotEmpty() && it.toString() != startingRepsTxt) {
                        viewModel.workoutIdToEdit = workoutSet.workoutId
                        workoutSet.reps = it.toString().toInt()
//                        Log.d(TAG, "set info:\nset: ${workoutSet.set}\nweight: ${workoutSet.weight}\nreps: ${workoutSet.reps}")
                        Log.d(TAG, "set: ${workoutSet.set}\nweight change triggered")
                        viewModel.updateSet(workoutSet)
                    }
                }
            }
        }

        private fun ifSetsAreRemoved(workoutSet: WorkoutSet) {
            binding.apply {
                if (setAreRemoved) {
                    // Check if sets can be removed
                    removeSetBtn.visibility = View.VISIBLE
                    spacer.visibility = View.VISIBLE
                    removeSetBtn.setOnClickListener {
                        viewModel.workoutIdToEdit = workoutSet.workoutId
                        workoutSet.set = setText.text.toString().toInt()
                        viewModel.removeSet(workoutSet)
                    }
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