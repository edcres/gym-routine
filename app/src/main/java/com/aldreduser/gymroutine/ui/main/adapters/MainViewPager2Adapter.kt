package com.aldreduser.gymroutine.ui.main.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.aldreduser.gymroutine.R
import com.aldreduser.gymroutine.ui.main.fragments.AllWorkoutsListFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

// ViewPager2 uses a recyclerView (this is the recyclerView Adapter)

class MainViewPager2Adapter (fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 3 // todo: make this return the number of categories +1 for the all tab (3 is a placeholder)
        //return Card.DECK.size
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = WorkoutGroupFragment()
        fragment.arguments = Bundle().apply {
            //todo: handle creation
            //ARG_OBJECT is a constant val key

            //   putInt(ARG_OBJECT, position + 1)
        }
        return fragment
    }
}

// Instances of this class are fragments representing a single
// object in our collection.
private const val ARG_OBJECT = "object"
class WorkoutGroupFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_all_workouts_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            //todo: handle creation
            //val textView: TextView = view.findViewById(android.R.id.text1)
            //textView.text = getInt(ARG_OBJECT).toString()
        }
        val viewPager: ViewPager2 = view.findViewById(R.id.workoutsListViewPager2)
        val tabLayout = mainTabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = "OBJECT ${(position + 1)}"
        }.attach()
    }
}