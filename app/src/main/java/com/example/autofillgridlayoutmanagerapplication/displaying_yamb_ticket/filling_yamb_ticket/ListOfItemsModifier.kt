package com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.filling_yamb_ticket

import com.example.autofillgridlayoutmanagerapplication.R
import com.example.autofillgridlayoutmanagerapplication.database.entities_and_data_classes.ColumnInYamb
import com.example.autofillgridlayoutmanagerapplication.database.entities_and_data_classes.Cubes
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.recyclerAdapter.ItemInGame
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.RowIndexOfResultElements
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.YambConstants
import java.lang.IllegalStateException


object ListOfItemsModifier {

    private var positionOfItemClicked = 0
    private var insertedValue = 0

    fun getModifiedListWhenValueInserted(position: Int, insertedValue: Int, currentListOfItems : List<ItemInGame>) : List<ItemInGame>{
        this.positionOfItemClicked = position
        this.insertedValue = insertedValue
        val tempList : MutableList<ItemInGame> = currentListOfItems as MutableList<ItemInGame>
        tempList[positionOfItemClicked] = getInsertedValueItem(insertedValue)
        updateResultItems(tempList)
        freezeItems(tempList)
        return tempList
    }
    private fun getInsertedValueItem(value : Int) : ItemInGame{
        return ItemInGame(R.layout.filling_yamb_ticket_text_element_in_recycler,data = value,clickable = false)
    }
    private fun updateResultItems(tempList : MutableList<ItemInGame>){
        when {
            isItemClickedInFirstSectionOfYambTicket() -> updateResultItemsOfFirstSection(tempList)
            isItemClickedInSecondSectionOfYambTicket() -> updateResultItemsOfSecondSection(tempList)
            else -> updateResultItemsOfThirdSection(tempList)
        }
    }
    private fun updateResultItemsOfFirstSection(tempList : MutableList<ItemInGame>){
        val column = positionOfItemClicked%6
        tempList[RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_ONE.index * 6 + column].data = (
                tempList[RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_ONE.index * 6 + column].data.toString().toInt() +
                        insertedValue
                ).toString()

        tempList[RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_ONE.index * 6 + 5].data = (
                tempList[RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_ONE.index * 6 + 5].data.toString().toInt() +
                        insertedValue
                ).toString()
    }
    private fun updateResultItemsOfSecondSection(tempList : MutableList<ItemInGame>){
        val column = positionOfItemClicked%6
        tempList[RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_TWO.index * 6 + column].data = (
                tempList[RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_TWO.index * 6 + column].data.toString().toInt() +
                        insertedValue
                ).toString()

        tempList[RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_TWO.index * 6 + 5].data = (
                tempList[RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_TWO.index * 6 + 5].data.toString().toInt() +
                        insertedValue
                ).toString()
    }
    private fun updateResultItemsOfThirdSection(tempList : MutableList<ItemInGame>){
        val column = positionOfItemClicked%6
        tempList[RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_THREE.index * 6 + column].data = (
                tempList[RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_THREE.index * 6 + column].data.toString().toInt() +
                        insertedValue
                ).toString()

        tempList[RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_THREE.index * 6 + 5].data = (
                tempList[RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_THREE.index * 6 + 5].data.toString().toInt() +
                        insertedValue
                ).toString()
    }

    fun generateStartingItems() : List<ItemInGame>{
        val tempList : MutableList<ItemInGame> = mutableListOf()
        var i = 0
        var layoutId = 0
        var data : Any? = null
        var clickable = false
        while(i<96){
            if(i%6 == 0){
                layoutId = R.layout.filling_yamb_ticket_image_element_in_recycler
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
                layoutId = R.layout.filling_yamb_ticket_text_element_in_recycler
                if((i%6 == 1 && i/6 == 0) || (i%6 == 2 && i/6 == 14) || (i%6 == 3))
                    clickable = true
                if(i/6 == 6 || i/6 == 9 || i/6 == 15)
                    clickable = false
            }
            if((i/6 == 6 || i/6 == 9 ||i/6 == 15 )&& i%6 != 0){
                data = 0
            }
            tempList.add(ItemInGame(layoutId = layoutId,data = data,clickable = clickable))
            data = null
            clickable = false
            i++
        }
        freezeItems(tempList)
        return tempList
    }
    fun generateStartingTestItems() : List<ItemInGame>{
        val tempList : MutableList<ItemInGame> = mutableListOf()
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
                    layoutId = R.layout.filling_yamb_ticket_image_element_in_recycler
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
                    layoutId = R.layout.filling_yamb_ticket_text_element_in_recycler
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
            tempList.add(ItemInGame(layoutId = layoutId, data = data, clickable = clickable))
            data = 0
            clickable = false
            i++

        }
        freezeItems(tempList)
        return tempList
    }
    fun getUnfreezedList(currentListOfItems: List<ItemInGame>):List<ItemInGame>{
      for((position,member) in currentListOfItems.withIndex()) {

            val column = position % YambConstants.COLUMN_NUMBER.value

            when(column){
                1-> checkShouldItemBeUnfrozenInArrowDownColumn(member, position,currentListOfItems)
                2-> checkShouldItemBeUnfrozenInArrowUpColumn(member, position,currentListOfItems)
                3-> checkShouldItemBeUnfrozenInArrowUpAndDownColumn(member)
            }
        }
        return currentListOfItems
    }
    private fun checkShouldItemBeUnfrozenInArrowDownColumn(item : ItemInGame, position : Int,currentListOfItems: List<ItemInGame>){
        val row = position/6
        if(item.data == null){
            if(row == 0)
                item.clickable = true
            else{
                if(isRowResult(row - 1) && currentListOfItems[position - YambConstants.COLUMN_NUMBER.value*2].data != null)
                    item.clickable = true
                if(currentListOfItems[position - YambConstants.COLUMN_NUMBER.value].data != null && !isRowResult(row - 1))
                    item.clickable = true
            }
        }
    }
    private fun checkShouldItemBeUnfrozenInArrowUpColumn(item : ItemInGame, position : Int,currentListOfItems: List<ItemInGame>) {
        val row = position/6

        if(item.data == null){
            if(row == 14)
                item.clickable = true
            else if(isRowResult(
                    row + 1
                ) && currentListOfItems[position +YambConstants.COLUMN_NUMBER.value*2].data != null)
                item.clickable = true
            else if(currentListOfItems[position + YambConstants.COLUMN_NUMBER.value].data != null&& !isRowResult(
                    row + 1
                )
            )
                item.clickable = true
        }
    }
    private fun checkShouldItemBeUnfrozenInArrowUpAndDownColumn(item : ItemInGame){
        if(item.data == null)
            item.clickable = true
    }

    private fun freezeItems(tempList: MutableList<ItemInGame>){
        for(member in tempList)
            member.clickable = false
    }
    fun getAheadCallPressedList(currentListOfItems: List<ItemInGame>):List<ItemInGame>{
        for((index,member) in currentListOfItems.withIndex()){
            if(index%YambConstants.COLUMN_NUMBER.value != 4)
                member.clickable = false
            else{
                if(!isRowResult(
                        index / YambConstants.COLUMN_NUMBER.value
                    ) && member.data == null)
                    member.clickable = true
            }

        }
        return currentListOfItems
    }
    fun getListWithFourObjectsEachRepresentingOneColumn(itemList : List<ItemInGame>,currentInsertedGameId : Long) : List<ColumnInYamb>{
        val mapOfPlayedColumns = getMapOfColumnsFromListOfItems(itemList)
        val listWithFourObjectsEachRepresentingOneColumn = mutableListOf<ColumnInYamb>()
        for((key,rowList) in mapOfPlayedColumns){
            listWithFourObjectsEachRepresentingOneColumn.add(key,
                ColumnInYamb(
                    columnId = 0,
                    correspondingGameId = currentInsertedGameId,
                    one = rowList[0],
                    two = rowList[1],
                    three = rowList[2],
                    four = rowList[3],
                    five = rowList[4],
                    six = rowList[5],
                    sum_form_one_to_six = rowList[6],
                    max = rowList[7],
                    min = rowList[8],
                    max_min = rowList[9],
                    two_pairs = rowList[10],
                    straight = rowList[11],
                    full = rowList[12],
                    poker = rowList[13],
                    yamb = rowList[14],
                    sum_from_two_pairs_to_yamb = rowList[15]
                )
            )
        }
        return listWithFourObjectsEachRepresentingOneColumn
    }
    fun generateListForRecyclerFromColumnsInDatabase(list : List<ColumnInYamb>) : List<ItemInGame>{

        val tempList = mutableListOf<ItemInGame>()
        var i = 0
        var layoutId : Int
        var data : Any?
        val clickable = false

        while(i<96){
            if(i%6 == 0){
                layoutId = R.layout.filling_yamb_ticket_image_element_in_recycler
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
                layoutId = R.layout.filling_yamb_ticket_text_element_in_recycler
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
            tempList.add(ItemInGame(layoutId = layoutId,data = data,clickable = clickable))
            i++
        }
        return tempList
    }

    private fun getMapOfColumnsFromListOfItems(currentListOfItems: List<ItemInGame>)  :Map<Int,List<Int?>>{

        val columnOne = mutableListOf<Int?>()
        val columnTwo = mutableListOf<Int?>()
        val columnThree = mutableListOf<Int?>()
        val columnFour = mutableListOf<Int?>()
        val columnFive = mutableListOf<Int?>()

        var data : Int? = 0

        for((index,member) in currentListOfItems.withIndex()){

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
    private fun isItemClickedInFirstSectionOfYambTicket() : Boolean{
        val row = positionOfItemClicked / 6
        return row < RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_ONE.index
    }
    private fun isItemClickedInSecondSectionOfYambTicket() : Boolean{
        val row = positionOfItemClicked / 6
        return row < RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_TWO.index && row > RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_ONE.index
    }
    private fun isRowResult(row : Int) : Boolean{
        return row == RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_ONE.index ||
                row == RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_TWO.index ||
                row == RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_THREE.index
    }



}