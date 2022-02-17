package com.aldreduser.gymroutine.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.aldreduser.gymroutine.data.WorkoutRepository
import com.aldreduser.gymroutine.data.model.room.WorkoutsRoomDatabase
import com.aldreduser.gymroutine.databinding.FragmentWorkoutsGroupListBinding
import com.aldreduser.gymroutine.ui.main.MainActivity
import com.aldreduser.gymroutine.ui.main.viewmodel.WorkoutListViewModel
import com.aldreduser.gymroutine.ui.main.viewmodel.WorkoutListViewModelFactory
import com.aldreduser.gymroutine.utils.FIRST_TAB_TITLE

class WorkoutsGroupListFragment : Fragment() {

    private var binding: FragmentWorkoutsGroupListBinding? = null
    private lateinit var workoutListViewModel: WorkoutListViewModel
    private var tabWorkoutGroup = FIRST_TAB_TITLE
//    private val recyclerviewAdapter: //todo: instantiate the list recyclerview adapter here

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentWorkoutsGroupListBinding.inflate(inflater ,container, false)
        binding = fragmentBinding
        setUpViewModel()
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = workoutListViewModel
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    // SET UP FUNCTIONS //
    private fun setUpViewModel() {
        val application = requireNotNull(this.activity).application
        val database = WorkoutsRoomDatabase.getInstance(application)
        val repository = WorkoutRepository(database)
        val viewModelFactory = WorkoutListViewModelFactory(repository, application)
        workoutListViewModel = ViewModelProvider(
                this, viewModelFactory).get(WorkoutListViewModel::class.java)
    }

    companion object{
        // The Fragment retrieves the Item from the List and sets the group category for this tab
        fun getInstance(titleId: Int): WorkoutsGroupListFragment {
            val thisHolderActivity = MainActivity()
            val thisWorkoutListFragment = WorkoutsGroupListFragment()
            val categoryToSet = thisHolderActivity.getViewModel().tabTitles[titleId]
            thisWorkoutListFragment.tabWorkoutGroup = categoryToSet
            return thisWorkoutListFragment
        }
    }
}
