package com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces

interface IOnGameClickedListener {
    fun showClickedGame(gameId : Long)
}
interface ISendTotalPoints{
    fun sendTotalPoints()
}
interface ISetLastItemClickedInPopUpDialog {
    fun setPopUpDialogTextForGivenPosition(positionOfItemClicked: Int)
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
interface IViewModelForDisplayingYambTicket {
    fun changeIsPopUpDialogEnabledState(position: Int,clickable : Boolean)
}

interface IHasObservers{
    fun disposeOfObservers()
}
