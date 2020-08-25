package com.example.autofillgridlayoutmanagerapplication.changing_fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentStateAdapter(private val fragmentManager: FragmentManager, private val lifecycle: Lifecycle, private var listOfFragments : List<Fragment>) : FragmentStateAdapter(fragmentManager, lifecycle) {


    override fun getItemCount(): Int {
        return listOfFragments.count()
    }

    override fun createFragment(position: Int): Fragment {
       return listOfFragments[position]
    }
}