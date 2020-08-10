package com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces


interface IUpdateRecyclerState {
    fun updateRecyclerState(positionOfItemSelectedInRecylcer: Int, valueForInput : Int)
}

interface ISetButtonForChangingFragments{
    fun setButtonForChangingFragments(value : Boolean)
    fun setDiceRolledInYamb(diceRolled : List<Int>)
    fun setAheadCallInYamb(aheadCall : Boolean)
}

interface ISetLastItemClickedInPopUpDialog {
    fun setPopUpDialogText(itemClicked : Int, row : Int,diceRolled : List<Int>)
}

interface IGetDiceRolledToFragmentYamb{
    fun getDiceRolled(diceRolled : List<Int>)
    fun getAheadCall(aheadCall : Boolean)
}