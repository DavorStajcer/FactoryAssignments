package com.example.autofillgridlayoutmanagerapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.main.bottom_sheet_dialog_layout.*

class PopUpWhenClickedDialogViewModel (
    var diceRolled: ArrayList<Int>,
    var positionOfItemClickedInRecycler: Int,
    var rowIndex: Int

)  : ViewModel(){

    private var valueForInput: Int = 0

    fun getTextForDisplay(): String {

        this.valueForInput = 0

        val MAX_INDEX = 7
        val MIN_INDEX = 8
        val TWO_PAIRS_INDEX = 10
        val FULL = 11
        val STRAIGHT = 12
        val POKER = 13
        val YAMB = 14

        Log.i("tag","$rowIndex")


        if (rowIndex < RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_ONE.index) {
            Log.i("tag","OD NULA DO 6")
            for (member in diceRolled)
                if (member == rowIndex + 1)
                    valueForInput += rowIndex + 1
            return "Zelite li upisati $valueForInput na poziciji $positionOfItemClickedInRecycler ?"
        }
        when (rowIndex) {
            MAX_INDEX -> {
                val array_of_max_or_min = diceRolled
                val temp = 0
                for ((index, member) in diceRolled.withIndex()) {
                    for ((index_two, number) in diceRolled.withIndex()) {
                        if (member > number) {
                            val temp = array_of_max_or_min[index]
                            array_of_max_or_min[index] = array_of_max_or_min[index_two]
                            array_of_max_or_min[index_two] = temp
                        }
                    }
                }
                Log.i("polje", "$array_of_max_or_min")
                for ((index, member) in array_of_max_or_min.withIndex()) {
                    if (index != array_of_max_or_min.size - 1)
                        valueForInput += member
                }

                   return "Zelite li upisati $valueForInput na poziciji $positionOfItemClickedInRecycler ?"
            }
            MIN_INDEX -> {
                val array_of_max_or_min = diceRolled
                val temp = 0
                for ((index, member) in diceRolled.withIndex()) {
                    for ((index_two, number) in diceRolled.withIndex()) {
                        if (member < number) {
                            val temp = array_of_max_or_min[index]
                            array_of_max_or_min[index] = array_of_max_or_min[index_two]
                            array_of_max_or_min[index_two] = temp
                        }
                    }
                }
                Log.i("polje", "$array_of_max_or_min")
                for ((index, member) in array_of_max_or_min.withIndex()) {
                    if (index != array_of_max_or_min.size - 1)
                        valueForInput += member
                }

                   return "Zelite li upisati $valueForInput na poziciji $positionOfItemClickedInRecycler ?"

            }
            TWO_PAIRS_INDEX -> {
                return "nesto"
            }
            STRAIGHT -> {
                return "nesto"
            }
            FULL -> {
                return "nesto"
            }
            POKER -> {
                return "nesto"
            }
            YAMB -> {
                return "nesto"
            }
            else -> throw IllegalArgumentException("Row number is out of range.")

        }

    }

    fun getValueForInput() : Int {
        return this.valueForInput
    }

}