package com.example.autofillgridlayoutmanagerapplication

interface ISetButtonClickableState {
    fun updateIsButtonClickable(keepItem: Boolean)
} //postavlja mi button koji mijenja fragmente na clickable ili ne, ovisno je li rezultat sacuvan, update se triggera u Main funkciji
interface ICubesRolledObserver {
    fun updateDiceRolled(diceRolled : MutableList<Int>, aheadCallInYamb : Boolean)
} //postavlja mi vrijednost polja bacenih kockica u fragment yamb, update se triggera u Fragment Yamb
interface ISetDiceRolledOnBottomSheetObserver {
    fun updateDiceRolledBottomSheet(diceRolled : MutableList<Int>)
} //postavlja mi vrijednost polja bacenih kockica u klasu BottomSheet, update se triggera u Bottom sheetu
interface ISetKeepItemRecyclerState {
    fun updateRecyclerKeepItemState(keepItem : Boolean,positionOfItemSelectedInRecylcer: Int, valueForInput : Int)
} //u recycleru se poziva update te mi se recycler updat-a ako se vrijednost treba sacuvati, a ako ne, ne radi se nista.
interface IUpdateLastItemClickedInRecycler{
    fun updateLastItemClicked(positionOfLastItemClicked : Int)
} //update se poziva u recycleru kada se pozove metoda onDetach od fragment yamba, tam ose postavljaju clickable itemi