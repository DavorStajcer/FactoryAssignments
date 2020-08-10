package com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.autofillgridlayoutmanagerapplication.R
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.recylcer.DataModel
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.RowIndexOfResultElements

enum class PopUpState(var position : Int = 0, var rowIndex: Int = 0){
    SHOW,HIDE
}

class FragmentYambViewModel : ViewModel() {

    var diceRolled = listOf<Int>()
    var aheadCall = false

    private val  isPopUpEnabled_ : MutableLiveData<PopUpState> = MutableLiveData()
    val isPopUpEnabled : LiveData<PopUpState>
        get()= isPopUpEnabled_

    private val mutableListOfFirstColumn = mutableListOf<DataModel>(
        DataModel(
            R.layout.grid_layout_element_image,
            R.drawable.cube1
        ),
        DataModel(
            R.layout.grid_layout_element_image,
            R.drawable.cube2
        ),
        DataModel(
            R.layout.grid_layout_element_image,
            R.drawable.cube3
        ),
        DataModel(
            R.layout.grid_layout_element_image,
            R.drawable.cube4
        ),
        DataModel(
            R.layout.grid_layout_element_image,
            R.drawable.cube5
        ),
        DataModel(
            R.layout.grid_layout_element_image,
            R.drawable.cube6
        ),
        DataModel(
            R.layout.grid_layout_element_image,
            R.drawable.zbroj_od_jedan_do_deset
        ),
        DataModel(
            R.layout.grid_layout_element_image,
            R.drawable.max
        ),
        DataModel(
            R.layout.grid_layout_element_image,
            R.drawable.min
        ),
        DataModel(
            R.layout.grid_layout_element_image,
            R.drawable.max_min
        ),
        DataModel(
            R.layout.grid_layout_element_image,
            R.drawable.dva_para
        ),
        DataModel(
            R.layout.grid_layout_element_image,
            R.drawable.straight
        ),
        DataModel(
            R.layout.grid_layout_element_image,
            R.drawable.full
        ),
        DataModel(
            R.layout.grid_layout_element_image,
            R.drawable.poker
        ),
        DataModel(
            R.layout.grid_layout_element_image,
            R.drawable.yamb
        ),
        DataModel(
            R.layout.grid_layout_element_image,
            R.drawable.zbroj_od_dva_para_do_yamba
        )
    )
    private val mutableListOfSecondColumns = mutableListOf<DataModel>(
        DataModel(
            R.layout.grid_layout_element_text_view,
            clickable = true
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        )
    )
    private val mutableListOfThirdColumn= mutableListOf<DataModel>(
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view,
            clickable = true
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        )
    )
    private val mutableListOfForuthColumn= mutableListOf<DataModel>(
        DataModel(
            R.layout.grid_layout_element_text_view,
            clickable = true
        ),
        DataModel(
            R.layout.grid_layout_element_text_view,
            clickable = true
        ),
        DataModel(
            R.layout.grid_layout_element_text_view,
            clickable = true
        ),
        DataModel(
            R.layout.grid_layout_element_text_view,
            clickable = true
        ),
        DataModel(
            R.layout.grid_layout_element_text_view,
            clickable = true
        ),
        DataModel(
            R.layout.grid_layout_element_text_view,
            clickable = true
        ),
        DataModel(
            R.layout.grid_layout_element_text_view,
            clickable = true
        ),
        DataModel(
            R.layout.grid_layout_element_text_view,
            clickable = true
        ),
        DataModel(
            R.layout.grid_layout_element_text_view,
            clickable = true
        ),
        DataModel(
            R.layout.grid_layout_element_text_view,
            clickable = true
        ),
        DataModel(
            R.layout.grid_layout_element_text_view,
            clickable = true
        ),
        DataModel(
            R.layout.grid_layout_element_text_view,
            clickable = true
        ),
        DataModel(
            R.layout.grid_layout_element_text_view,
            clickable = true
        ),
        DataModel(
            R.layout.grid_layout_element_text_view,
            clickable = true
        ),
        DataModel(
            R.layout.grid_layout_element_text_view,
            clickable = true
        ),
        DataModel(
            R.layout.grid_layout_element_text_view,
            clickable = true
        )
    )
    private val mutableListOfFifthColumn= mutableListOf<DataModel>(
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        )
    )
    private val mutableListOfSixthColumn= mutableListOf<DataModel>(
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        ),
        DataModel(
            R.layout.grid_layout_element_text_view
        )
    )
    private var  mutableMapOfElements = mutableMapOf<Int,MutableList<DataModel>>(
        0 to mutableListOfFirstColumn ,
        1 to  mutableListOfSecondColumns,
        2 to mutableListOfThirdColumn,
        3 to mutableListOfForuthColumn,
        4 to mutableListOfFifthColumn,
        5 to mutableListOfSixthColumn
    )

    private var positionOfLastItemClicked : Int = 0

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

    fun changePopUpState(position: Int,rowIndex: Int){
        PopUpState.SHOW.position = position
        PopUpState.SHOW.rowIndex = rowIndex
        isPopUpEnabled_.value = PopUpState.SHOW
        isPopUpEnabled_.value = PopUpState.HIDE
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
        this.mutableMapOfElements = mutableMapOfColumns
    }

    fun getMutableMapOfElements() : MutableMap<Int, MutableList<DataModel>>{
        return mutableMapOfElements
    }
}