package com.example.autofillgridlayoutmanagerapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bacanjekockica.Cube

class FragmentCubesViewModel : ViewModel() {

    val diceRolled: MutableLiveData<List<Int>> = MutableLiveData()
    val aheadCall: MutableLiveData<Boolean> = MutableLiveData()

    val cubes = mutableListOf<Cube>(Cube(), Cube(), Cube(), Cube(), Cube(), Cube())


    fun rollDice() {
        for (cube in cubes)
            cube.rollDice()
    }

    fun setDiceRolled() {
        val numbers = mutableListOf<Int>(0,0,0,0,0,0)
        for ((index, cube) in cubes.withIndex()) {
            when (cube.currentPicture) {
                R.id.ivCube1 -> numbers[index] = 1
                R.id.ivCube2 -> numbers[index] = 1
                R.id.ivCube3 -> numbers[index] = 1
                R.id.ivCube4 -> numbers[index] = 1
                R.id.ivCube5 -> numbers[index] = 1
                R.id.ivCube6 -> numbers[index] = 1
                else -> numbers[index] = 0
            }
        }
        this.diceRolled.value = numbers
    }

}