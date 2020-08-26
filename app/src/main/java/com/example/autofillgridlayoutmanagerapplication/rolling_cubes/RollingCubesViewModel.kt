package com.example.autofillgridlayoutmanagerapplication.rolling_cubes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.autofillgridlayoutmanagerapplication.R
import com.example.autofillgridlayoutmanagerapplication.database.entities_and_data_classes.Cubes
import com.example.autofillgridlayoutmanagerapplication.database.entities_and_data_classes.DataAboutRolledCubes
import com.example.autofillgridlayoutmanagerapplication.database.GamesPlayedDatabase
import com.example.autofillgridlayoutmanagerapplication.databinding.RollingCubesBindingData
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.IHasObservers
import com.example.bacanjekockica.Cube
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.IllegalStateException



class RollingCubesViewModel(val database: GamesPlayedDatabase) : ViewModel(), IHasObservers {



    private val setListeners_ = MutableLiveData<Boolean>()
    val setListeners : LiveData<Boolean>
        get() = setListeners_

    private val buttonForRollingCubesIsEnabled_= MutableLiveData<Boolean>(true)
    val buttonForRollingCubesIsEnabled : LiveData<Boolean>
        get() = buttonForRollingCubesIsEnabled_

    private val buttonForAheadCallIsEnabled_= MutableLiveData<Boolean>(false)
    val buttonForAheadCallIsEnabled : LiveData<Boolean>
        get() = buttonForAheadCallIsEnabled_

    private val databidingObject_ = MutableLiveData<RollingCubesBindingData>()
    val databidingObject : LiveData<RollingCubesBindingData>
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
        compositeDisposable.add( Completable.fromAction{
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
        )

        compositeDisposable.add(
            database.getDataAboutRolledCubesDao().getData(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(){
                    if(it.enableButtonForRollingDices){
                        buttonForRollingCubesIsEnabled_.value = true
                        buttonForAheadCallIsEnabled_.value = true
                    }
                }
        )


    }






    fun rollEachCube(){
        for (cube in cubes)
            cube.rollDice()
    }
    fun bindPictureData(){
        databidingObject_.value =
            RollingCubesBindingData(
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
    fun changeButtonsAndListeners(){
        buttonPressedCounter++
        if(isButtonPressedFirstTime()){
            setListeners_.value = !(setListeners.value ?: false)
            buttonForAheadCallIsEnabled_.value = true
        }
        else{
            setListeners_.value = !(setListeners.value ?: false)
            buttonForAheadCallIsEnabled_.value = false
            buttonForRollingCubesIsEnabled_.value = false
        }
    }
    fun saveRolledStatsIfRollingIsOver(){
        Log.i("rolled","Saving diceRolled")
        if(buttonPressedCounter == 2){
            saveDiceRolledAndAheadCallInDatabase()
            buttonPressedCounter = 0
        }
    }
    fun changeCubePressedState(indexOfCubePressed : Int){
        cubes[indexOfCubePressed].pressed = !cubes[indexOfCubePressed].pressed
    }
    fun changeAheadCall(){
        isAheadCallCalled = true
    }
    private fun isButtonPressedFirstTime() : Boolean{
        return buttonPressedCounter == 1
    }
    private fun saveDiceRolledAndAheadCallInDatabase() {

        val tempDiceRolled  : List<Int> = transformCubesPicturesResourcesIntoListOfNumberValues()
        val cubes =
            Cubes(cubeOne = tempDiceRolled[0], cubeTwo = tempDiceRolled[1], cubeThree = tempDiceRolled[2], cubeFour = tempDiceRolled[3], cubeFive = tempDiceRolled[4], cubeSix = tempDiceRolled[5])
        Log.i("rolled","cubes for saving : \n $cubes \n\n")
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

    override fun disposeOfObservers() {
        compositeDisposable.dispose()
    }


}