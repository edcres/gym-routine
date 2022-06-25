package com.aldreduser.gymroutine.ui.main.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.aldreduser.gymroutine.R
import com.aldreduser.gymroutine.data.model.entities.Workout
import com.aldreduser.gymroutine.databinding.FragmentStartBinding
import com.aldreduser.gymroutine.ui.main.adapters.GroupTabsAdapter
import com.aldreduser.gymroutine.ui.main.viewmodel.WorkoutListViewModel
import com.aldreduser.gymroutine.utils.findDifferentGroups
import com.aldreduser.gymroutine.utils.findDifferentName
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.workout_item.view.*

private const val fragmentTAG = "StartFrag__TAG"

class StartFragment : Fragment() {

    private var binding: FragmentStartBinding? = null
    private val viewModel: WorkoutListViewModel by activityViewModels()
    private lateinit var groupTabsAdapter: GroupTabsAdapter
    private lateinit var editWorkoutBtn: MenuItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentStartBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            addWorkoutFab.setOnClickListener { addWorkout() }
        }
        groupTabsAdapter = GroupTabsAdapter(this, viewModel)
        viewModel.startApplication(requireNotNull(this.activity).application)
        setUpAppBar()
        setUpTabs()
        setObservers()
        viewModel.setItemToEdit(null)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    // CLICK HANDLERS //
    private fun addWorkout() {
        viewModel.insertWorkout(
            Workout(
                workoutName = "",
                workoutGroup = viewModel.currentGroup
            )
        )
    }

    // SETUP //
    private fun setUpAppBar() {
        binding?.apply {
            mainActivityTopAppbar.title = "Workouts"
            mainActivityTopAppbar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.edit_workout_btn -> {
                        viewModel.toggleEditBtn()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun setUpTabs() {
        binding?.apply {
            workoutsListViewPager2.adapter = groupTabsAdapter
            TabLayoutMediator(
                mainTabLayout,
                workoutsListViewPager2
            ) { tab, position ->
                tab.text = viewModel.groupNames[position]
            }.attach()
        }
    }

    private fun setObservers() {
        // Observer for adding or removing tabs
        viewModel.menuEditIsOn.observe(viewLifecycleOwner) { result ->
            when (result) {
                true -> {
                    binding!!.mainActivityTopAppbar
                        .setBackgroundColor(resources.getColor(R.color.colorHighlight))
                }
                false -> {
                    binding!!.mainActivityTopAppbar
                        .setBackgroundColor(resources.getColor(R.color.colorPrimary))
                }
            }
        }
        viewModel.groups.observe(viewLifecycleOwner) {
            when {
                // '+1' because groupNames start out with FIRST_TAB_TITLE
                viewModel.groups.value!!.size+1 > viewModel.groupNames.size -> {
                    // New group(s) are added
                    val newGroupNames = findDifferentGroups(it, viewModel.groupNames)
                    viewModel.addTab(newGroupNames, groupTabsAdapter)
                }
                viewModel.groups.value!!.size < viewModel.groupNames.size -> {
                    // A group was removed
                    val removedGroupName = findDifferentName(viewModel.groupNames, it)
                    viewModel.removeTab(removedGroupName, groupTabsAdapter)
                }
                else -> {
                    Log.i(fragmentTAG, "setObservers: no group was added or removed")
                }
            }
        }
        viewModel.itemToEdit.observe(viewLifecycleOwner) {
            if(viewModel.itemToEdit.value != null) {
                val navController =
                    Navigation.findNavController(requireParentFragment().requireView())
                navController.navigate(R.id.action_startFragment_to_editWorkoutFragment)
            }
        }
    }
    // SETUP //
}