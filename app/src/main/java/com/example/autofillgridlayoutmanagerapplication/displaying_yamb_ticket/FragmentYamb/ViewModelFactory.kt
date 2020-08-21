package com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.FragmentYamb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.autofillgridlayoutmanagerapplication.database.GamesPlayedDatabase
import com.example.autofillgridlayoutmanagerapplication.savedGames.ViewModel_FragmentSavedGames


class ViewModelFactory(val database: GamesPlayedDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModel_FragmentYambTicket::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ViewModel_FragmentYambTicket(database) as T
        }
        if (modelClass.isAssignableFrom(ViewModel_FragmentSavedGames::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ViewModel_FragmentSavedGames(database) as T
        }

        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}