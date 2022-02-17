package com.aldreduser.gymroutine.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.aldreduser.gymroutine.data.WorkoutsRepository
import com.aldreduser.gymroutine.data.model.room.WorkoutsRoomDatabase
import com.aldreduser.gymroutine.databinding.FragmentWorkoutListBinding
import com.aldreduser.gymroutine.ui.main.adapters.WorkoutCategoryListAdapter
import com.aldreduser.gymroutine.ui.main.viewmodel.WorkoutListViewModel
import com.aldreduser.gymroutine.ui.main.viewmodel.WorkoutListViewModelFactory

class WorkoutListFragment : Fragment() {

    private val fragmentTAG = "WorkoutListFragmentTAG"
    private var binding: FragmentWorkoutListBinding? = null
    private val workoutListViewModel: WorkoutListViewModel by activityViewModels()
    private lateinit var recyclerviewAdapter: WorkoutCategoryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentWorkoutListBinding.inflate(inflater ,container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    // SET UP FUNCTIONS //

//    companion object{
//        // The Fragment retrieves the Item from the List and sets the group category for this tab
//        fun getInstance(titleId: Int): WorkoutListFragment {
//            val thisHolderActivity = MainActivity()
//            val thisWorkoutListFragment = WorkoutListFragment()
//            val categoryToSet = thisHolderActivity.getViewModel().tabTitles[titleId]
//            thisWorkoutListFragment.tabWorkoutGroup = categoryToSet
//            return thisWorkoutListFragment
//        }
//    }
}
