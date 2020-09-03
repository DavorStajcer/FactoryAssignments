package com.example.autofillgridlayoutmanagerapplication.view_model_factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.autofillgridlayoutmanagerapplication.changing_fragments.ChangingFragmentsViewModel
import com.example.autofillgridlayoutmanagerapplication.database.GamesPlayedDatabase
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.displaying_pop_up_dialog_when_game_finished.FinishedGamePopUpViewModel
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.filling_yamb_ticket.FillingYambTicketViewModel
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.displaying_pop_up_dialog_when_item_clicked.DisplayingPopUpForItemClickedViewModel
import com.example.autofillgridlayoutmanagerapplication.rolling_cubes.RollingCubesViewModel


class ViewModelFactory(val database: GamesPlayedDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FillingYambTicketViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FillingYambTicketViewModel(
                database
            ) as T
        }
        if (modelClass.isAssignableFrom(RollingCubesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RollingCubesViewModel(database) as T
        }
        if (modelClass.isAssignableFrom(DisplayingPopUpForItemClickedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DisplayingPopUpForItemClickedViewModel(database) as T
        }
        if (modelClass.isAssignableFrom(FinishedGamePopUpViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FinishedGamePopUpViewModel(database) as T
        }

        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}