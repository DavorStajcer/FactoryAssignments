package com.example.autofillgridlayoutmanagerapplication

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bacanjekockica.Cube

class FragmentCubesViewModel : ViewModel() {

    var diceRolled = MutableLiveData<ArrayList<Int>>()

    val cubes = mutableListOf<Cube>(Cube(), Cube(), Cube(), Cube(), Cube(), Cube())


    fun rollDice() {
        for (cube in cubes)
            cube.rollDice()
    }

    fun setRolledNumbers() {
        val numbers = arrayListOf<Int>()
        for ((index, cube) in cubes.withIndex()) {
                when (cube.currentPicture) {
                    R.drawable.cube1 -> numbers.add(1)
                    R.drawable.cube2 -> numbers.add(2)
                    R.drawable.cube3 -> numbers.add(3)
                    R.drawable.cube4 -> numbers.add(4)
                    R.drawable.cube5 ->numbers.add(5)
                    R.drawable.cube6 -> numbers.add(6)

                else ->   Log.i("tag", "\nnije ni jedna SLIKAAAAAAAAAAAAAAA")
            }

        }
        this.diceRolled.value = numbers
    }


}