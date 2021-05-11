package com.aldreduser.gymroutine.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.aldreduser.gymroutine.R
import com.aldreduser.gymroutine.databinding.FragmentWorkoutsGroupListBinding
import com.aldreduser.gymroutine.ui.main.viewmodels.WorkoutsListViewModel

class WorkoutsGroupListFragment : Fragment() {

    private var binding: FragmentWorkoutsGroupListBinding? = null
    private val workoutsListViewModel: WorkoutsListViewModel by activityViewModels()
//    private val recyclerviewAdapter: //todo: instantiate the list recyclerview adapter here

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentWorkoutsGroupListBinding.inflate(inflater ,container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = workoutsListViewModel
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}