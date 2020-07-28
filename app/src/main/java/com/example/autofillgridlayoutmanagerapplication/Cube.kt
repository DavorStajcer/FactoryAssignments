package com.example.bacanjekockica

import android.widget.ImageView
import com.example.autofillgridlayoutmanagerapplication.R
import kotlin.random.Random

class Cube(var pressed : Boolean = false, var currentPicture : Int = R.drawable.cube1){

    fun DiceRoll(lista : List<Int>){
        if(!this.pressed)
            this.currentPicture = lista[Random.nextInt(0,lista.size)] //ako mi nije stisnuta kocikce promjeni mi sliku na neko od njih 6 random
    }


}