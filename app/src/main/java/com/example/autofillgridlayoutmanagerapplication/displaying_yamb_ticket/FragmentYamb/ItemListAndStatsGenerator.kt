package com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.FragmentYamb

import com.example.autofillgridlayoutmanagerapplication.R
import com.example.autofillgridlayoutmanagerapplication.database.entities_and_data_classes.ColumnInYamb
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.recyclerAdapter.ItemInRecycler
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.YambConstants
import java.lang.IllegalStateException


object ItemListAndStatsGenerator {

    private var position = 0
    private var column : Int = 0
    private var row : Int = 0
    private var currentItems = mutableListOf<ItemInRecycler>()


    fun getNewList(position: Int,value: Int) : List<ItemInRecycler>{

        updateColumnRowAndPositionOfClickedItem(
            position
        )
        updateClickedItem(value)
        updateResultItems()

        return currentItems
    }
    fun getUnfreezedList():List<ItemInRecycler>{
      for((position,member) in currentItems.withIndex()) {

            column = position % YambConstants.COLUMN_NUMBER.value
            row = position / YambConstants.COLUMN_NUMBER.value


            when(column){
                1-> checkShouldItemBeUnfrozenInArrowDownColumn(
                    member,
                    position
                )
                2-> checkShouldItemBeUnfrozenInArrowUpColumn(
                    member,
                    position
                )
                3-> checkShouldItemBeUnfrozenInArrowUpAndDownColumn(
                    member
                )
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
                if(!isRowResult(
                        index / YambConstants.COLUMN_NUMBER.value
                    ) && member.data == null)
                    member.clickable = true
            }

        }
        return currentItems
    }
    fun isGameFinished() : Boolean{
        var isFinished = true
        for((index,member) in currentItems.withIndex()){
            if(member.data == null && index%6 != 5)
                isFinished = false
        }
        return isFinished
    }
    fun getTotalPoints() : Int{
        var totalPoints = 0
        for((index,member) in currentItems.withIndex()){
            if(index%YambConstants.COLUMN_NUMBER.value == 5)
                totalPoints += (member.data ?: 0).toString().toInt()
        }
        return totalPoints
    }
    fun generateStartingItems() : List<ItemInRecycler>{
        currentItems = mutableListOf()
        var i = 0
        var layoutId = 0
        var data : Any? = null
        var clickable = false
        while(i<96){
            if(i%6 == 0){
                layoutId = R.layout.image_element_recycler
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
                layoutId = R.layout.text_element_recycler
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

/*    fun generateStartingTestItems() : List<ItemInRecycler>{

        currentItems = mutableListOf<ItemInRecycler>()

        var i = 0
        var layoutId = 0
        var data: Any? = 0
        var clickable = false
        while (i < 96) {

            if(
                i%6 == 5 && i/6 !=
                RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_ONE.index && i/6 !=
                RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_TWO.index && i/6 !=
                RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_THREE.index
            )
                data = null
            else{
                if (i % 6 == 0) {
                    layoutId = R.layout.image_element_recycler
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
                }else {
                    layoutId = R.layout.text_element_recycler
                    if ( i/6 == 1 && i % 6 == 2) {
                        data = null
                        clickable = true
                    }
                    if(i/6 == 0 && i%6 == 2){
                        data = null
                        clickable = false
                    }

                }

            }
            currentItems.add(ItemInRecycler(layoutId = layoutId, data = data, clickable = clickable))
            data = 0
            clickable = false
            i++

        }
        return currentItems
    }*/

    fun generateListForRecyclerFromColumnsInDatabase(list : List<ColumnInYamb>) : List<ItemInRecycler>{

        val tempList = mutableListOf<ItemInRecycler>()
        var i = 0
        var layoutId : Int
        var data : Any?
        val clickable = false

        while(i<96){
            if(i%6 == 0){
                layoutId = R.layout.image_element_recycler
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
                layoutId = R.layout.text_element_recycler
                data = when(i/6){
                    0 ->  list[i%6 -1].one
                    1 -> list[i%6-1].two
                    2 -> list[i%6-1].three
                    3 -> list[i%6-1].four
                    4 -> list[i%6-1].five
                    5 -> list[i%6-1].six
                    6 -> list[i%6-1].sum_form_one_to_six
                    7 -> list[i%6-1].max
                    8 ->list[i%6-1].min
                    9 -> list[i%6-1].max_min
                    10 -> list[i%6-1].two_pairs
                    11 -> list[i%6-1].straight
                    12 ->list[i%6-1].full
                    13 -> list[i%6-1].poker
                    14 -> list[i%6-1].yamb
                    15 -> list[i%6-1].sum_from_two_pairs_to_yamb
                    else -> throw IllegalStateException("i out of bounds")
                }
            }
            tempList.add(ItemInRecycler(layoutId = layoutId,data = data,clickable = clickable))
            i++
        }
        return tempList
    }
    fun getMapOfColumnsFromListOfItems()  :Map<Int,List<Int?>>{

        val columnOne = mutableListOf<Int?>()
        val columnTwo = mutableListOf<Int?>()
        val columnThree = mutableListOf<Int?>()
        val columnFour = mutableListOf<Int?>()
        val columnFive = mutableListOf<Int?>()

        var data : Int? = 0

        for((index,member) in currentItems.withIndex()){

            data = if(member.data == null)
                null
            else
                member.data.toString().toInt()

            if(index % 6 == 1)
                columnOne.add(data)
            if(index % 6 == 2 )
                columnTwo.add(data)
            if(index % 6 == 3 )
                columnThree.add(data)
            if(index % 6 == 4)
                columnFour.add(data)
            if(index % 6 == 5)
                columnFive.add(data)
        }

        return mapOf<Int,List<Int?>>(
            0 to columnOne,
            1 to columnTwo,
            2 to columnThree,
            3 to columnFour,
            4 to columnFive
        )
    }

    private fun updateColumnRowAndPositionOfClickedItem(position : Int){
        ItemListAndStatsGenerator.position = position
        column = ItemListAndStatsGenerator.position %YambConstants.COLUMN_NUMBER.value
        row = ItemListAndStatsGenerator.position /YambConstants.COLUMN_NUMBER.value
}
    private fun updateClickedItem(valueOfClickedItem : Int){
        currentItems[position] =
            getNewItem(
                valueOfClickedItem
            )
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
                if(isRowResult(
                        row - 1
                    ) && currentItems[position - YambConstants.COLUMN_NUMBER.value*2].data != null)
                    item.clickable = true
                if(currentItems[position - YambConstants.COLUMN_NUMBER.value].data != null && !isRowResult(
                        row - 1
                    )
                )
                    item.clickable = true
            }
        }
    }
    private fun checkShouldItemBeUnfrozenInArrowUpColumn(item : ItemInRecycler,position : Int) {
        if(item.data == null){
            if(row == 14)
                item.clickable = true
            else if(isRowResult(
                    row + 1
                ) && currentItems[position +YambConstants.COLUMN_NUMBER.value*2].data != null)
                item.clickable = true
            else if(currentItems[position + YambConstants.COLUMN_NUMBER.value].data != null&& !isRowResult(
                    row + 1
                )
            )
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
        return ItemInRecycler(R.layout.text_element_recycler,data = value,clickable = false)
    }


}