package com.example.autofillgridlayoutmanagerapplication


interface IUpdateRecyclerState {
    fun updateRecyclerState(positionOfItemSelectedInRecylcer: Int, valueForInput : Int)
}

interface IGetDataForPassingToFragmentYamb{
    fun getDices(diceRolled: ArrayList<Int>)
    fun enableButtonForChangingFragments()
    fun getAheadCall(aheadCall : Boolean)
    fun getMutableMapOfElements(mutablemapOfElements : MutableMap<Int, MutableList<DataModel>>)
}

