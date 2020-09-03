package com.example.autofillgridlayoutmanagerapplication.view_model_factories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.autofillgridlayoutmanagerapplication.changing_fragments.ChangingFragmentsViewModel
import com.example.autofillgridlayoutmanagerapplication.displaying_saved_games.DisplayingSavedGamesViewModel

class SavedGamesViewModelFactory(val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(DisplayingSavedGamesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DisplayingSavedGamesViewModel(context) as T

        }
        if (modelClass.isAssignableFrom(ChangingFragmentsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChangingFragmentsViewModel(context) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}