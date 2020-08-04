package com.example.autofillgridlayoutmanagerapplication


interface IUpdateRecyclerState {
    fun updateRecyclerState(positionOfItemSelectedInRecylcer: Int, valueForInput : Int)
} //u recycleru se poziva update te mi se recycler updat-a ako se vrijednost treba sacuvati, a ako ne, ne radi se nista.
