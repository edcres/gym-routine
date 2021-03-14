package com.aldreduser.gymroutine.ui.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter


/*
from google: A TabLayout can be setup with a ViewPager in order to:
-Dynamically create TabItems based on the number of pages, their titles, etc.
-Synchronize the selected tab and tab indicator position with page swipes
 */

//im using this pager and adapter to dynamically add more tabs to my TabLayout in the MainActivity
class MainTabLayoutAdapter: RecyclerView.Adapter<CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(CardView(LayoutInflater.from(parent.context), parent))
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(Card.DECK[position])
    }

    override fun getItemCount(): Int {
        return Card.DECK.size
    }
}

class CardViewHolder internal constructor(private val cardView: CardView) :
    RecyclerView.ViewHolder(cardView.view) {
    internal fun bind(card: WorkoutCategory) {
        cardView.bind(card)
    }
}
