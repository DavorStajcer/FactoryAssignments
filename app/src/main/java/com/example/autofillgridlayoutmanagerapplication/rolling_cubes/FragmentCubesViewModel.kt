package com.example.autofillgridlayoutmanagerapplication.rolling_cubes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.autofillgridlayoutmanagerapplication.R
import com.example.autofillgridlayoutmanagerapplication.changing_fragments.MainActivity
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.ICubeDataReciver
import com.example.bacanjekockica.Cube
import java.lang.IllegalStateException



class FragmentCubesViewModel : ViewModel() {


    private val diceRolled_ = MutableLiveData<List<Int>>()
    val diceRolled : LiveData<List<Int>>
        get() = diceRolled_
    private val aheadCall_ = MutableLiveData<Boolean>()
    val aheadCall : LiveData<Boolean>
        get() = aheadCall_
    private val buttonForChangingFragmentsIsEnabled_ = MutableLiveData<Boolean>()
        val buttonForChangingFragmentsIsEnabled : LiveData<Boolean>
        get() = buttonForChangingFragmentsIsEnabled_

    var cubeDataListener : ICubeDataReciver? = null
    val setListeners = MutableLiveData<Boolean>()
    val buttonForRollingCubesIsEnabled = MutableLiveData<Boolean>()
    val buttonForAheadCallIsEnabled = MutableLiveData<Boolean>(false)

    private val pictures = mapOf<Int,Int>(
        0 to R.drawable.cube1,
        1 to R.drawable.cube2,
        2 to R.drawable.cube3,
        3 to R.drawable.cube4,
        4 to R.drawable.cube5,
        5 to R.drawable.cube6
    )

    val cubes = MutableLiveData(
        mutableListOf(
            Cube(pictures = pictures), Cube(pictures = pictures),
            Cube(pictures = pictures), Cube(pictures = pictures),
            Cube(pictures = pictures), Cube(pictures = pictures)
        )
    )


    private var buttonPressedCounter = 0

    fun rollDice() {
        val cubesForRolling = cubes.value
        for (cube in cubesForRolling ?: throw IllegalStateException("Value of cubes mutableLiveData is null!?"))
            cube.rollDice()
        cubes.value = cubesForRolling
        buttonPressedCounter++
        if(buttonPressedCounter == 1){
            aheadCall_.value = false
            setListeners.value = !(setListeners.value ?: false)
            buttonForAheadCallIsEnabled.value = true
        }
        else{
            buttonForChangingFragmentsIsEnabled_.value = true
            buttonForRollingCubesIsEnabled.value = false
            buttonForAheadCallIsEnabled.value = false
            buttonPressedCounter = 0
            setRolledNumbers()
        }
    }

    private fun setRolledNumbers() {

        val numbers = mutableListOf<Int>()
        for ((index, cube) in (cubes.value ?: throw IllegalStateException("Value of cubes mutableLiveData is null!?") ).withIndex()) {
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
        this.diceRolled_.value = numbers
    }

    fun changeCubePressedState(cubeNumber : Int){
        (cubes.value ?: throw IllegalStateException("Value of cubes mutableLiveData is null!?") )[cubeNumber].pressed = !(cubes.value ?: throw IllegalStateException("Value of cubes mutableLiveData is null!?"))[cubeNumber].pressed
    }
    fun setButtonsClickable(){
        buttonForRollingCubesIsEnabled.value = true
        buttonForAheadCallIsEnabled.value = false
        buttonForChangingFragmentsIsEnabled_.value = false
    }
    fun changeAheadCall(){
        aheadCall_.value = true
    }

    fun sendAheadCallToActivity(state : Boolean){
        cubeDataListener?.setAheadCallInYamb(this.aheadCall.value!!)
    }
    fun sendDiceRolledToActivity(diceRolled : List<Int>){
        cubeDataListener?.setDiceRolledInYamb(diceRolled)
    }
    fun changeBottonForChangingFragmentsState(state : Boolean){
        cubeDataListener?.setButtonForChangingFragments(state)
    }

}