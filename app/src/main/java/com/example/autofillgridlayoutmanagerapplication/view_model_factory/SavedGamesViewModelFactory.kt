package com.example.autofillgridlayoutmanagerapplication.view_model_factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.autofillgridlayoutmanagerapplication.changing_fragments.ChangingFragmentsViewModel
import com.example.autofillgridlayoutmanagerapplication.database.GamesPlayedDatabase
import com.example.autofillgridlayoutmanagerapplication.displaying_saved_games.DisplayingSavedGamesViewModel
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.filling_yamb_ticket.FillingYambTicketViewModel
import com.example.autofillgridlayoutmanagerapplication.rolling_cubes.RollingCubesViewModel

class SavedGamesViewModelFactory(val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(DisplayingSavedGamesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DisplayingSavedGamesViewModel(context) as T

        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}