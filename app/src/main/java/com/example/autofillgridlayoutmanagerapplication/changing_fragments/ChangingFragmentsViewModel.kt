package com.example.autofillgridlayoutmanagerapplication.changing_fragments

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.autofillgridlayoutmanagerapplication.R
import com.example.autofillgridlayoutmanagerapplication.database.entities_and_data_classes.Cubes
import com.example.autofillgridlayoutmanagerapplication.database.entities_and_data_classes.DataAboutRolledCubes
import com.example.autofillgridlayoutmanagerapplication.database.GamesPlayedDatabase
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.IHasObservers
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ChangingFragmentsViewModel(val context: Context) : ViewModel()  {
    val fragemetnNames = listOf<String>(
        context.getString(R.string.past_games_fragment),
        context.getString(R.string.roll_dices_fragment),
        context.getString(R.string.yamb_ticket_fragment))
}