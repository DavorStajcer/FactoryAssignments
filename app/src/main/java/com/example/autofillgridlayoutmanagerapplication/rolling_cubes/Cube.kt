package com.example.bacanjekockica

import android.util.Log
import android.widget.ImageView
import com.example.autofillgridlayoutmanagerapplication.R
import kotlin.random.Random

class Cube(var pressed : Boolean = false, var currentPicture : Int = R.drawable.cube1,private val pictures : Map<Int,Int>){



    fun rollDice(){
        if(!this.pressed){
            this.currentPicture = pictures[Random.nextInt(0,pictures.size)] ?: R.drawable.cube1
        }
    }


}