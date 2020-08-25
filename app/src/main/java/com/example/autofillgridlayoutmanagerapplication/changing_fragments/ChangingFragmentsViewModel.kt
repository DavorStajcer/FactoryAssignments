package com.example.autofillgridlayoutmanagerapplication.changing_fragments

import androidx.lifecycle.ViewModel
import com.example.autofillgridlayoutmanagerapplication.database.entities_and_data_classes.Cubes
import com.example.autofillgridlayoutmanagerapplication.database.entities_and_data_classes.DataAboutRolledCubes
import com.example.autofillgridlayoutmanagerapplication.database.GamesPlayedDatabase
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.IHasObservers
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ChangingFragmentsViewModel(val database: GamesPlayedDatabase) : ViewModel()  {

    val fragemetnNames = listOf<String>("PAST GAMES","ROLL DICES","YAMB TICKET")

}