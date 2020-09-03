package com.example.autofillgridlayoutmanagerapplication.list_and_game_data_modifiers

import com.example.autofillgridlayoutmanagerapplication.R
import com.example.autofillgridlayoutmanagerapplication.database.entities_and_data_classes.Cubes
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.filling_yamb_ticket.recyclerAdapter.ItemInGame
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.YambConstants

object GameDataModifier {
    fun generateDiceRolledWithDataFromDatabase(cubesRolled : Cubes) : List<Int>{
        val tempDiceRolledList = mutableListOf<Int>()
        tempDiceRolledList.add(cubesRolled.cubeOne)
        tempDiceRolledList.add(cubesRolled.cubeTwo)
        tempDiceRolledList.add(cubesRolled.cubeThree)
        tempDiceRolledList.add(cubesRolled.cubeFour)
        tempDiceRolledList.add(cubesRolled.cubeFive)
        tempDiceRolledList.add(cubesRolled.cubeSix)
        return tempDiceRolledList
    }
    fun mapNumbersRolledToPicutresForDisplaying(diceRolled : List<Int>) : List<Int>{
        val tempPictureSourceList = mutableListOf<Int>()
        for(numberRolled in diceRolled){
            when(numberRolled){
                1 -> tempPictureSourceList.add(R.drawable.cube1)
                2 -> tempPictureSourceList.add(R.drawable.cube2)
                3 -> tempPictureSourceList.add(R.drawable.cube3)
                4 -> tempPictureSourceList.add(R.drawable.cube4)
                5 -> tempPictureSourceList.add(R.drawable.cube5)
                6 -> tempPictureSourceList.add(R.drawable.cube6)
                else -> throw IllegalArgumentException("ViewModelPopUpItemWhenClicked -> diceRolled list containes numbers diffrent than numbers from 1-6 !? This is madness !..Madness ?..This ! Is ! Andorid Studio !")
            }
        }
        return tempPictureSourceList
    }
    fun isGameFinished(currentListOfItems: List<ItemInGame>) : Boolean{
        var isFinished = true
        for((index,member) in currentListOfItems.withIndex()){
            if(member.data == null && index%6 != 5)
                isFinished = false
        }
        return isFinished
    }
    fun getTotalPoints(currentListOfItems: List<ItemInGame>) : Int{
        var totalPoints = 0
        for((index,member) in currentListOfItems.withIndex()){
            if(index% YambConstants.COLUMN_NUMBER.value == 5)
                totalPoints += (member.data ?: 0).toString().toInt()
        }
        return totalPoints
    }
}