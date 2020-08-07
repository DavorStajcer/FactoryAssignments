package com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.pop_up_dialog

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.autofillgridlayoutmanagerapplication.R
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.RowIndexOfResultElements

class PopUpWhenClickedDialogViewModel (
)  : ViewModel(){


    private var valueForInput: Int = 0
    var positionOfItemClickedInRecycler = 0
    private var diceRolled = listOf<Int>()


    private val text_ = MutableLiveData<String>()
    val text : LiveData<String>
        get() = text_
    private val pictureSources_ = MutableLiveData<List<Int>>()
    val pictureSources : LiveData<List<Int>>
        get() = pictureSources_




    private fun setCubePictureSoruce() {


        val pictureSourceList = mutableListOf<Int>()

        Log.i("row","PopUp, ViewModel, setCubePictureSource(), diceRolled -> $diceRolled")

        for((index,numberRolled) in diceRolled.withIndex()){
            when(numberRolled){
                1 -> pictureSourceList.add(R.drawable.cube1)
                2 -> pictureSourceList.add(R.drawable.cube2)
                3 -> pictureSourceList.add(R.drawable.cube3)
                4 -> pictureSourceList.add(R.drawable.cube4)
                5 -> pictureSourceList.add(R.drawable.cube5)
                6 -> pictureSourceList.add(R.drawable.cube6)
                else -> Log.i("tag","POP UP BOTTROM SHEET -> getCubePictureSource -> ne odgovara ni jedna slika")
            }
        }
        pictureSources_.value = pictureSourceList
    }

    fun setTextForDisplay(positionOfItemClickedInRecycler: Int,rowIndex: Int) {

        Log.i("row","POPuP,ViewModel , row -> $rowIndex")

        this.valueForInput = 0
        this.positionOfItemClickedInRecycler = positionOfItemClickedInRecycler

        val MAX_INDEX = 7
        val MIN_INDEX = 8
        val TWO_PAIRS_INDEX = 10
        val FULL = 11
        val STRAIGHT = 12
        val POKER = 13
        val YAMB = 14


        val array_of_max_or_min = diceRolled as MutableList<Int>

        if (rowIndex < RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_ONE.index) {
            for (member in diceRolled)
                if (member == rowIndex + 1)
                    valueForInput += rowIndex + 1

            text_.value = "Zelite li upisati $valueForInput na poziciji $positionOfItemClickedInRecycler ?"
        }else {


            when (rowIndex) {
                MAX_INDEX -> {

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

                    for ((index, member) in array_of_max_or_min.withIndex()) {
                        if (index != array_of_max_or_min.size - 1)
                            valueForInput += member
                    }

                    text_.value =
                        "Zelite li upisati $valueForInput na poziciji $positionOfItemClickedInRecycler ?"
                }
                MIN_INDEX -> {

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

                    for ((index, member) in array_of_max_or_min.withIndex()) {
                        if (index != array_of_max_or_min.size - 1)
                            valueForInput += member
                    }

                    text_.value =
                        "Zelite li upisati $valueForInput na poziciji $positionOfItemClickedInRecycler ?"

                }
                TWO_PAIRS_INDEX -> {
                    text_.value = "nesto"
                }
                STRAIGHT -> {
                    text_.value = "nesto"
                }
                FULL -> {
                    text_.value = "nesto"
                }
                POKER -> {
                    text_.value = "nesto"
                }
                YAMB -> {
                    text_.value = "nesto"
                }
                else -> throw IllegalArgumentException("Row number is out of range.")

            }
        }
        setCubePictureSoruce()
    }

    fun getValueForInput() : Int {
        return this.valueForInput
    }

    fun setDiceRolled(diceRolled : MutableList<Int>){
        this.diceRolled = diceRolled
    }




}