package com.example.autofillgridlayoutmanagerapplication.changing_fragments

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.autofillgridlayoutmanagerapplication.database.EntitiesAndDataCalsses.Cubes
import com.example.autofillgridlayoutmanagerapplication.database.EntitiesAndDataCalsses.DataAboutRolledCubes
import com.example.autofillgridlayoutmanagerapplication.database.GamesPlayedDatabase
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainAcitivtyViewModel() : ViewModel() {

    var context : Context? = null
    private var disposable : Disposable? = null

    fun initializeDataAboutRolledCubes(){

            val cubes =
                Cubes(
                    cubeOne = 1,
                    cubeTwo = 1,
                    cubeThree = 1,
                    cubeFour = 1,
                    cubeFive = 1,
                    cubeSix = 1
                )

        disposable = Completable.fromAction {
            GamesPlayedDatabase.getInstanceOfDatabase(context!!).getDataAboutRolledCubesDao().insertData(
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
            .subscribe()

    }
}