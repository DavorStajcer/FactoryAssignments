package com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces

interface IOnGameClickedListener {
    fun showClickedGame(gameId : Long)
}
interface ISendTotalPoints{
    fun sendTotalPoints()
}
interface ISetLastItemClickedInPopUpDialog {
    fun changeDataForBindingOfItemClickedPopUp(positionOfItemClicked: Int)
}
interface IGetPickedItemData{
    fun getValueInsertedInClickedItem(valueOfItemPicked : Int)
}
interface IDisplayPopUpListener{
    fun sendPositionOfItemClickedToPopUpDialog()
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
