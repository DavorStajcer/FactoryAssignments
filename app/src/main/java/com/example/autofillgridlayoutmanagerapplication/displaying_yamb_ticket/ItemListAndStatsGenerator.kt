package com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket

import android.util.Log
import com.example.autofillgridlayoutmanagerapplication.R
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.recylcer.ItemInRecycler
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.RowIndexOfResultElements
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.YambConstants
import java.lang.IllegalStateException


object ItemListAndStatsGenerator {

    private var position = 0
    private var column : Int = 0
    private var row : Int = 0
    private var currentItems = mutableListOf<ItemInRecycler>()


    fun getNewList(position: Int,value: Int) : List<ItemInRecycler>{

        updateColumnRowAndPositionOfClickedItem(position)
        updateClickedItem(value)
        updateResultItems()

        return currentItems
    }
    fun getUnfreezedList():List<ItemInRecycler>{



      for((position,member) in currentItems.withIndex()) {

            this.column = position % YambConstants.COLUMN_NUMBER.value
            this.row = position / YambConstants.COLUMN_NUMBER.value


            when(column){
                1-> checkShouldItemBeUnfrozenInArrowDownColumn(member,position)
                2-> checkShouldItemBeUnfrozenInArrowUpColumn(member,position)
                3-> checkShouldItemBeUnfrozenInArrowUpAndDownColumn(member)
            }

        }
        return currentItems
    }
    fun getFreezedList() : List<ItemInRecycler>{
        for(member in currentItems)
            member.clickable = false
        return currentItems
    }
    fun getAheadCallPressedList():List<ItemInRecycler>{
        for((index,member) in currentItems.withIndex()){
            if(index%YambConstants.COLUMN_NUMBER.value != 4)
                member.clickable = false
            else{
                if(!isRowResult(index/YambConstants.COLUMN_NUMBER.value) && member.data == null)
                    member.clickable = true
            }

        }
        return currentItems
    }
    fun isGameFinished() : Boolean{
        var isFinished = true
        for(member in currentItems){
            if(member.data == null)
                isFinished = false
        }
        return isFinished
    }
    fun getTotalPoints() : Int{
        var totalPoints = 0
        for((index,member) in currentItems.withIndex()){
            if(index%YambConstants.COLUMN_NUMBER.value == 5)
                totalPoints += member.data.toString().toInt()
        }
        return totalPoints
    }
    fun generateStartingItems() : List<ItemInRecycler>{
        this.currentItems = mutableListOf()
        var i = 0
        var layoutId = 0
        var data : Any? = null
        var clickable = false
        while(i<96){
            if(i%6 == 0){
                layoutId = R.layout.grid_layout_element_image
                data = when(i/6){
                    0 ->  R.drawable.cube1
                    1 -> R.drawable.cube2
                    2 -> R.drawable.cube3
                    3 -> R.drawable.cube4
                    4 -> R.drawable.cube5
                    5 -> R.drawable.cube6
                    6 -> R.drawable.zbroj_od_jedan_do_deset
                    7 -> R.drawable.max
                    8 -> R.drawable.min
                    9 -> R.drawable.max_min
                    10 -> R.drawable.dva_para
                    11 -> R.drawable.straight
                    12 -> R.drawable.full
                    13 -> R.drawable.poker
                    14 -> R.drawable.yamb
                    15 -> R.drawable.zbroj_od_dva_para_do_yamba
                    else -> throw IllegalStateException("i out of bounds")
                }
            }else{
                layoutId = R.layout.grid_layout_element_text_view
                if((i%6 == 1 && i/6 == 0) || (i%6 == 2 && i/6 == 14) || (i%6 == 3))
                    clickable = true
                if(i/6 == 6 || i/6 == 9 || i/6 == 15)
                    clickable = false
            }
            if((i/6 == 6 || i/6 == 9 ||i/6 == 15 )&& i%6 != 0){
                data = 0
            }
            currentItems.add(ItemInRecycler(layoutId = layoutId,data = data,clickable = clickable))
            data = null
            clickable = false
            i++
        }

        return currentItems
    }

    private fun updateColumnRowAndPositionOfClickedItem(position : Int){
        this.position = position
        this.column = this.position%YambConstants.COLUMN_NUMBER.value
        this.row = this.position/YambConstants.COLUMN_NUMBER.value

}
    private fun  updateClickedItem(valueOfClickedItem : Int){
        currentItems[position] = getNewItem(valueOfClickedItem)
    }
    private fun updateResultItems(){

        when {
            isItemClickedInFirstSectionOfYambTicket() -> updateResultItemsOfFirstSection()
            isItemClickedInSecondSectionOfYambTicket() -> updateResultItemsOfSecondSection()
            else -> updateResultItemsOfThirdSection()
        }

    }

    private fun isItemClickedInFirstSectionOfYambTicket() : Boolean{
        return row < RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_ONE.index
    }
    private fun isItemClickedInSecondSectionOfYambTicket() : Boolean{
        return row < RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_TWO.index && row > RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_ONE.index
    }
    private fun updateResultItemsOfFirstSection(){

        currentItems[RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_ONE.index * 6 + column].data = (
                currentItems[RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_ONE.index * 6 + column].data.toString().toInt() +
                        currentItems[position].data.toString().toInt()
                ).toString()

        currentItems[RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_ONE.index * 6 + 5].data = (
                currentItems[RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_ONE.index * 6 + 5].data.toString().toInt() +
                        currentItems[position].data.toString().toInt()
                ).toString()
    }
    private fun updateResultItemsOfSecondSection(){
        currentItems[RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_TWO.index * 6 + column].data = (
                currentItems[RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_TWO.index * 6 + column].data.toString().toInt() +
                        currentItems[position].data.toString().toInt()
                ).toString()

        currentItems[RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_TWO.index * 6 + 5].data = (
                currentItems[RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_TWO.index * 6 + 5].data.toString().toInt() +
                        currentItems[position].data.toString().toInt()
                ).toString()
    }
    private fun updateResultItemsOfThirdSection(){
        currentItems[RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_THREE.index * 6 + column].data = (
                currentItems[RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_THREE.index * 6 + column].data.toString().toInt() +
                        currentItems[position].data.toString().toInt()
                ).toString()

        currentItems[RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_THREE.index * 6 + 5].data = (
                currentItems[RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_THREE.index * 6 + 5].data.toString().toInt() +
                        currentItems[position].data.toString().toInt()
                ).toString()
    }


    private fun checkShouldItemBeUnfrozenInArrowDownColumn(item : ItemInRecycler, position : Int){

        if(item.data == null){
            if(row == 0)
                item.clickable = true
            else{
                if(isRowResult(row-1) && currentItems[position - YambConstants.COLUMN_NUMBER.value*2].data != null)
                    item.clickable = true
                if(currentItems[position - YambConstants.COLUMN_NUMBER.value].data != null && !isRowResult(row -1))
                    item.clickable = true
            }
        }
    }
    private fun checkShouldItemBeUnfrozenInArrowUpColumn(item : ItemInRecycler,position : Int) {
        if(item.data == null){
            if(row == 14)
                item.clickable = true
            else if(isRowResult(row+1) && currentItems[position +YambConstants.COLUMN_NUMBER.value*2].data != null)
                item.clickable = true
            else if(currentItems[position + YambConstants.COLUMN_NUMBER.value].data != null&& !isRowResult(row +1))
                item.clickable = true
        }
    }
    private fun checkShouldItemBeUnfrozenInArrowUpAndDownColumn(item : ItemInRecycler){
        if(item.data == null)
            item.clickable = true
    }
    private fun isRowResult(row : Int) : Boolean{
        return row == RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_ONE.index ||
                row == RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_TWO.index ||
                row == RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_THREE.index
    }

    private fun getNewItem(value : Int) : ItemInRecycler{
        return ItemInRecycler(R.layout.grid_layout_element_text_view,data = value,clickable = false)
    }

    fun generateStartingTestItems() : List<ItemInRecycler>{

        var i = 0
        var layoutId = 0
        var data: Any? = 0
        var clickable = false
        while (i < 96) {
            if (i % 6 == 0) {
                layoutId = R.layout.grid_layout_element_image
                data = when (i / 6) {
                    0 -> R.drawable.cube1
                    1 -> R.drawable.cube2
                    2 -> R.drawable.cube3
                    3 -> R.drawable.cube4
                    4 -> R.drawable.cube5
                    5 -> R.drawable.cube6
                    6 -> R.drawable.zbroj_od_jedan_do_deset
                    7 -> R.drawable.max
                    8 -> R.drawable.min
                    9 -> R.drawable.max_min
                    10 -> R.drawable.dva_para
                    11 -> R.drawable.straight
                    12 -> R.drawable.full
                    13 -> R.drawable.poker
                    14 -> R.drawable.yamb
                    15 -> R.drawable.zbroj_od_dva_para_do_yamba
                    else -> throw IllegalStateException("i out of bounds")
                }
            } else {
                layoutId = R.layout.grid_layout_element_text_view
                if (i / 6 == 0 && i % 6 == 2) {
                    data = null
                    clickable = true
                }
            }
            currentItems.add(ItemInRecycler(layoutId = layoutId, data = data, clickable = clickable))
            data = 0
            clickable = false
            i++

        }
        return currentItems
    } //ostaje mi samo jos jedan item za klikunt, da vidim je li funkcionira zavrsavanje igre kako treba










}