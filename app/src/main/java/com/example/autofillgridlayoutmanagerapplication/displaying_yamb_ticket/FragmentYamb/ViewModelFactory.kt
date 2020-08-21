package com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.FragmentYamb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.autofillgridlayoutmanagerapplication.changing_fragments.MainAcitivtyViewModel
import com.example.autofillgridlayoutmanagerapplication.database.GamesPlayedDatabase
import com.example.autofillgridlayoutmanagerapplication.savedGames.ViewModelSavedGames


class ViewModelFactory(val database: GamesPlayedDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelFragmentYambTicket::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ViewModelFragmentYambTicket(database) as T
        }
        if (modelClass.isAssignableFrom(ViewModelSavedGames::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ViewModelSavedGames(database) as T
        }
        if (modelClass.isAssignableFrom(MainAcitivtyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainAcitivtyViewModel(database) as T
        }

        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}