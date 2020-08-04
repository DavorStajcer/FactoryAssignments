package com.example.autofillgridlayoutmanagerapplication

import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FragmentYambViewModel : ViewModel() {

    var diceRolled : ArrayList<Int> = arrayListOf<Int>()
    var aheadCall: Boolean = false
    var positionOfLastItemClicked : Int = 0

    var mutableMapOfElements  = hashMapOf<Int,MutableList<DataModel>>()

    fun updateLastItemClicked() {

        Log.i("CLICKED", "$positionOfLastItemClicked")

        if (!mutableMapOfElements[1]!![0].isValueSet)
            mutableMapOfElements[1]!![0].clickable = true
        if (!mutableMapOfElements[2]!![14].isValueSet)
            mutableMapOfElements[2]!![14].clickable = true

        setNextItemClickable(mutableMapOfElements)

        for ((column, arreydDataModel) in mutableMapOfElements) {
            for ((row, dataModel) in arreydDataModel.withIndex()) {
                if (column == 3 && !mutableMapOfElements[column]!![row].isValueSet)
                    mutableMapOfElements[column]!![row].clickable = true
            }
        }
    }

    private fun getItemPosition(numberOfColumns : Int,columnIndex : Int, rowIndex :Int) : Int{
        return numberOfColumns*rowIndex + columnIndex
    }

    private fun setNextItemClickable(mutableMapOfColumns : MutableMap<Int,MutableList<DataModel>>) {

        for((column,arrayDataModel) in mutableMapOfColumns){
            for((row,dataModel) in arrayDataModel.withIndex()){
                if(column == 1 && !dataModel.isValueSet){
                    if(row != RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_ONE.index && row != RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_TWO.index  && row != RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_THREE.index){
                        dataModel.clickable = true
                        break
                    }
                }
                if(column == 2 && dataModel.isValueSet){
                    if(row == 0)
                        break
                    if(row == RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_TWO.index + 1 || row == RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_ONE.index + 1){
                        Log.i("ajde","dole -> ${getItemPosition(6,column,row)}")
                        mutableMapOfColumns[column]!![row - 2].clickable = true
                        break
                    }
                    Log.i("ajde","dole, preskok -> ${getItemPosition(6,column,row)}")
                    mutableMapOfColumns[column]!![row - 1].clickable = true
                    break
                }
            }
        }
        this.mutableMapOfElements = mutableMapOfColumns as HashMap<Int, MutableList<DataModel>>
    }



}