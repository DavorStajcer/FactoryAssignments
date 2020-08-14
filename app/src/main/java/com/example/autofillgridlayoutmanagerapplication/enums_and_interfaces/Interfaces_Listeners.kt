package com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces


interface ICubeDataReciver{
    fun setButtonForChangingFragments(value : Boolean)
    fun setDiceRolledInYamb(diceRolled : List<Int>)
    fun setAheadCallInYamb(aheadCall : Boolean)
}

interface IFinishedGameListener{
    fun changeResultScreenState()
    fun changeTotalPointsState(totalPoints : Int)
}

interface ISetLastItemClickedInPopUpDialog {
    fun setPopUpDialogText(positionOfItemClicked: Int,diceRolled : List<Int>)
}

interface IGetDiceRolledToFragmentYamb{
    fun getDiceRolled(diceRolled : List<Int>)
    fun getAheadCall(aheadCall : Boolean)
}

interface IGetPickedItemData{
    fun getPickedItemData(valueOfItemPicked : Int)
}


interface IDisplayPopUpListener{
    fun enableSendingDataToPopUp()
}