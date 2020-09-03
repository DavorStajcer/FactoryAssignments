package com.example.autofillgridlayoutmanagerapplication.changing_fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.autofillgridlayoutmanagerapplication.displaying_saved_games.DisplayingSavedGamesFragment
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.filling_yamb_ticket.FillingYambTicketFragment
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.filling_yamb_ticket.recyclerAdapter.DisplayingYambGameRecyclerAdapter
import com.example.autofillgridlayoutmanagerapplication.rolling_cubes.RollingCubesFragment
import java.lang.IllegalStateException

class FragmentStateAdapter(private val fragmentManager: FragmentManager, private val lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val NUMBER_OF_FRAGMENTS = 3

    override fun getItemCount(): Int {
        return NUMBER_OF_FRAGMENTS
    }
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> DisplayingSavedGamesFragment()
            1 -> RollingCubesFragment()
            2 -> FillingYambTicketFragment()
            else -> throw IllegalStateException("Position out of bounds")
        }
    }
}