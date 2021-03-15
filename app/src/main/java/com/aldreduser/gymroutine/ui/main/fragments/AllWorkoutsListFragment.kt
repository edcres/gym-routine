package com.aldreduser.gymroutine.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.aldreduser.gymroutine.R
import com.aldreduser.gymroutine.ui.main.adapters.MainViewPager2Adapter
import kotlinx.android.synthetic.main.activity_main.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WorkoutsListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class WorkoutsListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var mainViewPager2Adapter: MainViewPager2Adapter
    private lateinit var viewPager: ViewPager2

    //the tutorial by android didn't have this function (but it's here by default)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_workouts_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mainViewPager2Adapter = MainViewPager2Adapter(this)
        viewPager = view.findViewById(R.id.workoutsListViewPager2)
        viewPager.adapter = mainViewPager2Adapter
        //super.onViewCreated(view, savedInstanceState)     //the tutorial by android didn't have this line (but it's here by default)
    }

    // this is not used but it's here by default
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WorkoutsListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}