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

class MainAcitivtyViewModel(val database: GamesPlayedDatabase) : ViewModel() , IHasObservers {

    lateinit var disposable: Disposable

    init {
    val cubes =
        Cubes(
            cubeOne = 1,
            cubeTwo = 1,
            cubeThree = 1,
            cubeFour = 1,
            cubeFive = 1,
            cubeSix = 1
        )
    disposable = Completable.fromAction{
        database.getDataAboutRolledCubesDao().insertData(
            DataAboutRolledCubes(
                1,
                cubes,
                aheadCall = false,
                isRecyclerFrozen = true,
                enableButtonForRollingDices = true
            )
        )
    }.subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe(){

    }
}

    override fun disposeOfObservers(){
        disposable.dispose()
    }


}