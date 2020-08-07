package com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces


interface IUpdateRecyclerState {
    fun updateRecyclerState(positionOfItemSelectedInRecylcer: Int, valueForInput : Int)
}

interface ISetButtonForChangingFragments{
    fun setButtonForChangingFragments(value : Boolean)
}

interface ISetLastItemClickedInPopUpDialog {
    fun setPopUpDialogText(itemClicked : Int, row : Int,diceRolled : MutableList<Int>)
}