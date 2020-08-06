package com.example.bacanjekockica

import android.util.Log
import android.widget.ImageView
import com.example.autofillgridlayoutmanagerapplication.R
import kotlin.random.Random

class Cube(var pressed : Boolean = false, var currentPicture : Int = R.drawable.cube1){

    private val pictures = mapOf<Int,Int>(
        0 to R.drawable.cube1,
        1 to R.drawable.cube2,
        2 to R.drawable.cube3,
        3 to R.drawable.cube4,
        4 to R.drawable.cube5,
        5 to R.drawable.cube6
    )

    fun rollDice(){
        if(!this.pressed){
            this.currentPicture = pictures[Random.nextInt(0,pictures.size)] ?: R.drawable.cube1
        }
    }


}