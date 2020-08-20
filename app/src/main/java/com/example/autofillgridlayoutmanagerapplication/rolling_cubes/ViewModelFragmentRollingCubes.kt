package com.example.autofillgridlayoutmanagerapplication.rolling_cubes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.autofillgridlayoutmanagerapplication.R
import com.example.autofillgridlayoutmanagerapplication.database.EntitiesAndDataCalsses.Cubes
import com.example.autofillgridlayoutmanagerapplication.database.EntitiesAndDataCalsses.DataAboutRolledCubes
import com.example.autofillgridlayoutmanagerapplication.database.GamesPlayedDatabase
import com.example.autofillgridlayoutmanagerapplication.databinding.FragmentCubesDataForBinding
import com.example.bacanjekockica.Cube
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.IllegalStateException



class ViewModelFragmentRollingCubes : ViewModel() {


    private val setListeners_ = MutableLiveData<Boolean>()
    val setListeners : LiveData<Boolean>
        get() = setListeners_

    private val buttonForRollingCubesIsEnabled_= MutableLiveData<Boolean>()
    val buttonForRollingCubesIsEnabled : LiveData<Boolean>
        get() = buttonForRollingCubesIsEnabled_

    private val buttonForAheadCallIsEnabled_= MutableLiveData<Boolean>(false)
    val buttonForAheadCallIsEnabled : LiveData<Boolean>
        get() = buttonForAheadCallIsEnabled_

    private val databidingObject_ = MutableLiveData<FragmentCubesDataForBinding>()
    val databidingObject : LiveData<FragmentCubesDataForBinding>
        get() = databidingObject_


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

    private var buttonPressedCounter = 0
    private var isAheadCallCalled = false
    private var compositeDisposable = CompositeDisposable()
    lateinit var database: GamesPlayedDatabase

    fun rollDice() {
        rollEachCube()
        bindPictureData()
        buttonPressedCounter++
        if(isButtonPressedFirstTime()){
            setListeners_.value = !(setListeners.value ?: false)
            buttonForAheadCallIsEnabled_.value = true
        }
        else{
            buttonForAheadCallIsEnabled_.value = false
            buttonForRollingCubesIsEnabled_.value = true
            buttonPressedCounter = 0
            saveDiceRolledAndAheadCallInDatabase()
        }
    }
    fun changeCubePressedState(cubeNumber : Int){
        cubes[cubeNumber].pressed = !cubes[cubeNumber].pressed
    }
    fun changeAheadCall(){
        isAheadCallCalled = true
    }
    fun checkIsButtonForRollingCubesEnabled(){

        compositeDisposable.add(
        database.getDataAboutRolledCubesDao().getData(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(){
                this.buttonForRollingCubesIsEnabled_.value = it.enableButtonForRollingDices
            }
        )
    }

    private fun rollEachCube(){
        for (cube in cubes)
            cube.rollDice()
    }
    private fun bindPictureData(){
        databidingObject_.value =
            FragmentCubesDataForBinding(
                pictureOne = cubes[0].currentPicture,
                pictureTwo = cubes[1].currentPicture,
                pictureThree = cubes[2].currentPicture,
                pictureFour = cubes[3].currentPicture,
                pictureFive = cubes[4].currentPicture,
                pictureSix = cubes[5].currentPicture,
                buttonAheadCallText = "Ahead Call",
                btnRollDicesText = "Roll Dices"
            )
    }
    private fun isButtonPressedFirstTime() : Boolean{
        return buttonPressedCounter == 1
    }
    private fun saveDiceRolledAndAheadCallInDatabase() {

        val tempDiceRolled  : List<Int> = transformCubesPicturesResourcesIntoListOfNumberValues()
        val cubes =
            Cubes(cubeOne = tempDiceRolled[0], cubeTwo = tempDiceRolled[1], cubeThree = tempDiceRolled[2], cubeFour = tempDiceRolled[3], cubeFive = tempDiceRolled[4], cubeSix = tempDiceRolled[5])

        compositeDisposable.add(
            Completable.fromAction {
            database.getDataAboutRolledCubesDao().insertData(
                DataAboutRolledCubes(1, cubes, isAheadCallCalled, isRecyclerFrozen = false, enableButtonForRollingDices = false)
            )
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(){
                Log.i("database","Completed -> data saved")
            }
        )

    }
    private fun transformCubesPicturesResourcesIntoListOfNumberValues()  :List<Int>{

        val numbers = mutableListOf<Int>()
        for (cube in cubes) {
            when (cube.currentPicture) {
                R.drawable.cube1 -> numbers.add(1)
                R.drawable.cube2 -> numbers.add(2)
                R.drawable.cube3 -> numbers.add(3)
                R.drawable.cube4 -> numbers.add(4)
                R.drawable.cube5 ->numbers.add(5)
                R.drawable.cube6 -> numbers.add(6)
                else ->    throw IllegalStateException("Index value of cube field is more than 5 !?")
            }

        }
        return numbers
    }



}