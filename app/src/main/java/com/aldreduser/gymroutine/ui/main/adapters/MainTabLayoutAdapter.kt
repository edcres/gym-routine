package com.aldreduser.gymroutine.ui.main.adapters

import android.view.View
import androidx.viewpager.widget.PagerAdapter


/*
from google: A TabLayout can be setup with a ViewPager in order to:
-Dynamically create TabItems based on the number of pages, their titles, etc.
-Synchronize the selected tab and tab indicator position with page swipes
 */

//im using this pager and adapter to dynamically add more tabs to my TabLayout in the MainActivity
class MainTabLayoutAdapter: PagerAdapter() {

    override fun getCount(): Int {
        TODO("Not yet implemented")
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        TODO("Not yet implemented")
    }

    override fun getPageTitle(position: Int): CharSequence? {
        // todo: Return tab text label for position
    }
}