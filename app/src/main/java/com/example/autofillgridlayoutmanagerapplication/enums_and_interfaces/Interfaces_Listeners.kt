package com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces

interface IOnGameClickedListener {
    fun showClickedGame(gameId : Long)
}
interface ISendTotalPoints{
    fun sendTotalPoints()
}
interface IOnItemInRecyclerClickedListener{
    fun activatePopUpWhenItemClicked(positionOfItemClicked: Int)
}
interface ISetLastItemClickedInPopUpDialog {
    fun setPopUpDialogText(positionOfItemClicked: Int)
}
interface IGetPickedItemData{
    fun getPickedItemDataFromPopUpForInsertingValues(valueOfItemPicked : Int)
}
interface IDisplayPopUpListener{
    fun enableSendingDataToPopUpForInsertingValues()
}
interface ISaveOrContinueListener{
    fun saveGame()
    fun resetItems()
}