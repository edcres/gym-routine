package com.aldreduser.gymroutine.ui.main.fragments

//todo: delete this file

//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.activityViewModels
//import androidx.lifecycle.ViewModelProvider
//import androidx.viewpager2.widget.ViewPager2
//import com.aldreduser.gymroutine.R
//import com.aldreduser.gymroutine.data.WorkoutRepository
//import com.aldreduser.gymroutine.data.model.room.WorkoutsRoomDatabase
//import com.aldreduser.gymroutine.databinding.FragmentAllWorkoutsListBinding
//import com.aldreduser.gymroutine.ui.main.adapters.AllWorkoutsListAdapter
//import com.aldreduser.gymroutine.ui.main.adapters.MainViewPager2Adapter
//import com.aldreduser.gymroutine.ui.main.viewmodels.WorkoutsListViewModel
//import com.aldreduser.gymroutine.ui.main.viewmodels.WorkoutsListViewModelFactory
//import com.google.android.material.tabs.TabLayoutMediator
//import kotlinx.android.synthetic.main.activity_main.*
//
//class AllWorkoutsListFragment : Fragment() {
//
//    private var binding: FragmentAllWorkoutsListBinding? = null
//    private lateinit var workoutsListViewModel: WorkoutsListViewModel
//    //    private val recyclerviewAdapter:
//
//    private lateinit var mainViewPager2Adapter: MainViewPager2Adapter
//    private lateinit var viewPager: ViewPager2
//
//    override fun onCreateView(
//            inflater: LayoutInflater,
//            container: ViewGroup?,
//            savedInstanceState: Bundle?
//    ): View? {
//        val fragmentBinding = FragmentAllWorkoutsListBinding.inflate(inflater, container,false)
//        binding = fragmentBinding
//        setUpViewModel()
//        return fragmentBinding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding?.apply {
//            lifecycleOwner = viewLifecycleOwner
//            viewModel = workoutsListViewModel
//        }
//
//        mainViewPager2Adapter = MainViewPager2Adapter(this)
//        viewPager = view.findViewById(R.id.workouts_list_view_pager2)
//        viewPager.adapter = mainViewPager2Adapter
//
//        val tabLayout = main_tab_layout
//        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//            tab.text = "OBJECT ${(position + 1)}"
//        }.attach()
//
//        setUpRecyclerView()
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        binding = null
//    }
//
//    // SET UP FUNCTIONS //
//    private fun setUpViewModel() {
//        val application = requireNotNull(this.activity).application
//        val database = WorkoutsRoomDatabase.getInstance(application)
//        val repository = WorkoutRepository.getInstance(database)
//        val viewModelFactory = WorkoutsListViewModelFactory(repository, application)
//        workoutsListViewModel = ViewModelProvider(
//                this, viewModelFactory).get(WorkoutsListViewModel::class.java)
//    }
//
//    private fun setUpRecyclerView() {
//        val adapter = AllWorkoutsListAdapter()
//        binding?.workoutsRecyclerview?.adapter = adapter
//    }
//}






// its layout
//<?xml version="1.0" encoding="utf-8"?>
//<layout xmlns:app="http://schemas.android.com/apk/res-auto"
//xmlns:android="http://schemas.android.com/apk/res/android"
//xmlns:tools="http://schemas.android.com/tools">
//
//<data>
//<variable
//name="viewModel"
//type="com.aldreduser.gymroutine.ui.main.viewmodels.WorkoutsListViewModel" />
//</data>
//
//<FrameLayout
//android:layout_width="match_parent"
//android:layout_height="match_parent"
//tools:context=".ui.main.fragments.AllWorkoutsListFragment">
//
//<androidx.recyclerview.widget.RecyclerView
//android:id="@+id/workouts_recyclerview"
//android:layout_width="match_parent"
//android:layout_height="wrap_content"
//android:layout_margin="6dp"
//app:layoutManager="androidx.recyclerview.widget.GridLayoutManager"
//app:spanCount="2"
//tools:listitem="@layout/workouts_recycler_item" />
//
//</FrameLayout>
//
//</layout>