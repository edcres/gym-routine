package com.aldreduser.gymroutine.ui.main.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aldreduser.gymroutine.data.model.entities.Workout
import com.aldreduser.gymroutine.databinding.WorkoutItemBinding
import com.aldreduser.gymroutine.ui.main.viewmodel.WorkoutListViewModel
import com.aldreduser.gymroutine.utils.*

class WorkoutListAdapter(
    private val workoutsViewModel: WorkoutListViewModel,
    private val context: Context,
    private val fragLifecycleOwner: LifecycleOwner
) : ListAdapter<Workout, WorkoutListAdapter.WorkoutsViewHolder>(WorkoutDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        WorkoutsViewHolder.from(workoutsViewModel, context, fragLifecycleOwner, parent)

    override fun onBindViewHolder(holderWorkouts: WorkoutsViewHolder, position: Int) =
        holderWorkouts.bind(getItem(position))

    class WorkoutsViewHolder private constructor(
        private val viewModel: WorkoutListViewModel,
        private val context: Context,
        private val fragLifecycleOwner: LifecycleOwner,
        private val binding: WorkoutItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var setsAdapter: SetsAdapter

        fun bind(workout: Workout) {
            binding.apply {
                setsAdapter = SetsAdapter(viewModel, false)

                if (specificWorkoutInput.text.isNullOrEmpty())
                    specificWorkoutInput.setText(workout.workoutName)

                updateWorkoutName(workout)
                setUpSets(workout)
                observeEditMode()
                observeHiddenTxt()

                editItemBtn.setOnClickListener {
                    viewModel.workoutIdToEdit = workout.id
                    viewModel.setItemToEdit(workout)
                }
                removeItemBtn.setOnClickListener {
                    viewModel.removeWorkout(workout, workout.workoutGroup)
                }
                binding.executePendingBindings()
            }
        }

        private fun observeHiddenTxt() {
            viewModel.hiddenTxt.observe(fragLifecycleOwner) {
                binding.apply {
                    when (dummyTxt.text.toString()) {
                        "a" -> dummyTxt.text = "a"
                        "b" -> dummyTxt.text = "b"
                        else -> dummyTxt.text = "a"
                    }
                }
            }
        }

        private fun observeEditMode() {
            viewModel.menuEditIsOn.observe(fragLifecycleOwner) { result ->
                binding.apply {
                    when (result) {
                        true -> {
                            editItemBtn.visibility = View.VISIBLE
                            removeItemBtn.visibility = View.VISIBLE
                        }
                        false -> {
                            editItemBtn.visibility = View.GONE
                            removeItemBtn.visibility = View.GONE
                        }
                    }
                }
            }
        }

        private fun updateWorkoutName(workout: Workout) {
            binding.specificWorkoutInput.doAfterTextChanged {
                workout.workoutName = it.toString()
                viewModel.updateWorkoutName(workout)
            }
        }

        private fun setUpSets(workout: Workout) {
            binding.setListRecycler.adapter = setsAdapter
            binding.setListRecycler.layoutManager = CustomLinearLayoutManager(context)
            viewModel.getSetsOfWorkout(workout.id)
                .observe(fragLifecycleOwner) { theseSets ->
                    setsAdapter.submitList(theseSets)
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
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: Workout, newItem: Workout): Boolean {
        return oldItem == newItem
    }
}