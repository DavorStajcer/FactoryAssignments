package com.example.autofillgridlayoutmanagerapplication.rolling_cubes


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.autofillgridlayoutmanagerapplication.R
import com.example.autofillgridlayoutmanagerapplication.database.entities_and_data_classes.Cubes
import com.example.autofillgridlayoutmanagerapplication.database.entities_and_data_classes.DataAboutRolledCubes
import com.example.autofillgridlayoutmanagerapplication.database.GamesPlayedDatabase
import com.example.autofillgridlayoutmanagerapplication.databinding.*
import com.example.autofillgridlayoutmanagerapplication.list_and_game_data_modifiers.ListOfItemsModifier
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.IHasObservers
import com.example.bacanjekockica.Cube
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class RollingCubesViewModel(val database: GamesPlayedDatabase) : ViewModel(), IHasObservers {

    private val rollingCubesUI_ = MutableLiveData<RollingCubesUiState>()
    val rollingCubesUI : LiveData<RollingCubesUiState>
        get() = rollingCubesUI_

    private val pictures = mapOf<Int,Int>(
        0 to R.drawable.cube1,
        1 to R.drawable.cube2,
        2 to R.drawable.cube3,
        3 to R.drawable.cube4,
        4 to R.drawable.cube5,
        5 to R.drawable.cube6
    )
    private val cubes =
        listOf(
            Cube(pictures = pictures), Cube(pictures = pictures),
            Cube(pictures = pictures), Cube(pictures = pictures),
            Cube(pictures = pictures), Cube(pictures = pictures)
        )
    private var compositeDisposable = CompositeDisposable()
    private var isAheadCallCalled = false

    init {
        rollingCubesUI_.value = AfterSecondRollUiState
        saveStartingCubesInDatabaseAndSetUpObserverForCubesData()
    }

    fun rollCubesAndChangeUiState(){
        for (cube in cubes)
            cube.rollDice()
        rollingCubesUI_.value = rollingCubesUI_.value!!.getNewState(cubes)
        if(rollingCubesUI_.value == AfterSecondRollUiState || rollingCubesUI_.value == AfterSecondRollUiStateAheadCallPressed){
            saveDiceRolledAndAheadCallInDatabase()
            resetClicksOnCubes()
        }
    }
    fun changeCubePressedState(indexOfCubePressed : Int){
        cubes[indexOfCubePressed].pressed = !cubes[indexOfCubePressed].pressed
    }
    fun changeUiAfterAheadCallButtonIsPressed(){
        rollingCubesUI_.value = AfterAheadCallPressedUiState
        isAheadCallCalled = true
    }
    private fun saveStartingCubesInDatabaseAndSetUpObserverForCubesData() {
        val cubes = Cubes(cubeOne = 1, cubeTwo = 1, cubeThree = 1, cubeFour = 1, cubeFive = 1, cubeSix = 1)
        compositeDisposable.add(
            Completable.fromAction{
                database.getDataAboutRolledCubesDao().insertData(
                    DataAboutRolledCubes(
                        dataRolledCubesId = 1,
                        cubes = cubes,
                        aheadCall = false,
                        isRecyclerFrozen = true,
                        enableRollingDices = true
                    )
                )
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    setUpObserverAboutCubesData()
                }){
                    throw it
                }
        )
    }
    private fun resetClicksOnCubes(){
        for(member in cubes)
            member.pressed = false
    }
    private fun setUpObserverAboutCubesData(){
        compositeDisposable.add(
            database.getDataAboutRolledCubesDao().getData(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(){
                    if(it.enableRollingDices)
                        rollingCubesUI_.value = rollingCubesUI_.value!!.getNewState(cubes)
                }
        )
    }
    private fun saveDiceRolledAndAheadCallInDatabase() {
        val tempDiceRolled  : List<Int> = ListOfItemsModifier.transformCubesPicturesResourcesIntoListOfNumberValues(cubes)
        val cubes = Cubes(cubeOne = tempDiceRolled[0], cubeTwo = tempDiceRolled[1], cubeThree = tempDiceRolled[2], cubeFour = tempDiceRolled[3], cubeFive = tempDiceRolled[4], cubeSix = tempDiceRolled[5])
        compositeDisposable.add(
            Completable.fromAction {
            database.getDataAboutRolledCubesDao().insertData(
                DataAboutRolledCubes(1, cubes, isAheadCallCalled, isRecyclerFrozen = false, enableRollingDices = false)
            )
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(){
                isAheadCallCalled = false
            }
        )

    }
    override fun disposeOfObservers() {
        compositeDisposable.dispose()
    }


}