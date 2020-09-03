package com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.displaying_pop_up_dialog_when_item_clicked

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.autofillgridlayoutmanagerapplication.database.GamesPlayedDatabase
import com.example.autofillgridlayoutmanagerapplication.databinding.DisplayingPopUpDialogWhenItemClickedBindingData
import com.example.autofillgridlayoutmanagerapplication.list_and_game_data_modifiers.GameDataModifier
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.IHasObservers
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.RowIndexOfResultElements
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


enum class SendDataOfItemChosen(var positionOfItemChosen : Int, var valueForInput : Int){
    ENABLED(0,0),DISABLED(0,0)
}

class DisplayingPopUpForItemClickedViewModel (val database: GamesPlayedDatabase)  : ViewModel(), IHasObservers{

    private val sendingInsertedValueOfChosenItem_ = MutableLiveData<SendDataOfItemChosen>()
    val sendingInsertedValueOfChosenItem : LiveData<SendDataOfItemChosen>
        get() = sendingInsertedValueOfChosenItem_

    private val dataForBinding_ = MutableLiveData<DisplayingPopUpDialogWhenItemClickedBindingData>()
    val dataForBinding : LiveData<DisplayingPopUpDialogWhenItemClickedBindingData>
        get() = dataForBinding_

    private var diceRolled = listOf<Int>()
    private var positionOfItemClickedInRecycler = 0
    private var valueForInput = 0
    private val compositeDisposable = CompositeDisposable()

     private val MAX_INDEX = 7
     private val MIN_INDEX = 8
     private val TWO_PAIRS_INDEX = 10
     private val FULL = 11
     private val STRAIGHT = 12
     private val POKER = 13
     private val YAMB = 14


    init {
        setDiceRolledDatabaseObserver()
        setPositionOfItemClickedAndTotalPointsObserver()
    }


    private fun changeUi(positionOfItemClickedInRecycler: Int) {
        this.positionOfItemClickedInRecycler = positionOfItemClickedInRecycler
        this.valueForInput = 0
        val textForDisplay = getTextForDisplay()
        val pictureSourceList : List<Int> = GameDataModifier.mapNumbersRolledToPicutresForDisplaying(diceRolled)
        changeDataforBinding(textForDisplay,pictureSourceList)
    }

    fun sendDataOfItemPickedBack(){
        SendDataOfItemChosen.ENABLED.valueForInput = valueForInput
        sendingInsertedValueOfChosenItem_.value = SendDataOfItemChosen.ENABLED
        sendingInsertedValueOfChosenItem_.value = SendDataOfItemChosen.DISABLED

    }


    private fun setDiceRolledDatabaseObserver(){
        compositeDisposable.add(
            database.getDataAboutRolledCubesDao().getData(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    this.diceRolled = GameDataModifier.generateDiceRolledWithDataFromDatabase(it.cubes)
                }){
                    throw it
                }
        )
    }
    private fun setPositionOfItemClickedAndTotalPointsObserver(){
        compositeDisposable.add(
            database.getPopUpsDataDao().getPopUpsData(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(){
                    this.positionOfItemClickedInRecycler = it.positionOfItemClicked
                    changeUi(positionOfItemClickedInRecycler)
                }
        )
    }
    private fun changeDataforBinding(textForDisplay : String, pictureSourceList : List<Int>){
        dataForBinding_.value = DisplayingPopUpDialogWhenItemClickedBindingData(pictureSourceList[0], pictureSourceList[1], pictureSourceList[2], pictureSourceList[3], pictureSourceList[4], pictureSourceList[5], textForDisplay)
    }
    private fun getTextForDisplay() : String {
        val rowIndex = positionOfItemClickedInRecycler / 6
        return if(rowIsInTheFirstSection())
            getTextForItemsFromOneToSix()
        else {
            when (rowIndex) {
                MAX_INDEX -> getTextForMaxItem()
                MIN_INDEX -> getTextForMinItem()
                TWO_PAIRS_INDEX -> "nesto"
                STRAIGHT -> "nesto"
                FULL -> "nesto"
                POKER -> "nesto"
                YAMB -> "nesto"
                else -> "ERROR"
            }
        }
    }
    private fun rowIsInTheFirstSection() : Boolean {
        val rowIndex = positionOfItemClickedInRecycler / 6
        return rowIndex < RowIndexOfResultElements.INDEX_OF_RESULT_ROW_ELEMENT_ONE.index
    }
    private fun getTextForMinItem() : String{
        val array_of_max_or_min = diceRolled as MutableList<Int>
        for ((index, member) in diceRolled.withIndex()) {
            for ((index_two, number) in diceRolled.withIndex()) {
                if (member < number) {
                    val temp = array_of_max_or_min[index]
                    array_of_max_or_min[index] = array_of_max_or_min[index_two]
                    array_of_max_or_min[index_two] = temp
                }
            }
        }
        for ((index, member) in array_of_max_or_min.withIndex()) {
            if (index != array_of_max_or_min.size - 1)
                valueForInput += member
        }
        return "Zelite li upisati $valueForInput na poziciji $positionOfItemClickedInRecycler ?"
    }
    private fun getTextForMaxItem() : String {
        val array_of_max_or_min = diceRolled as MutableList<Int>
        for ((index, member) in diceRolled.withIndex()) {
            for ((index_two, number) in diceRolled.withIndex()) {
                if (member > number) {
                    val temp = array_of_max_or_min[index]
                    array_of_max_or_min[index] = array_of_max_or_min[index_two]
                    array_of_max_or_min[index_two] = temp
                }
            }
        }

        for ((index, member) in array_of_max_or_min.withIndex()) {
            if (index != array_of_max_or_min.size - 1)
                valueForInput += member
        }
        return  "Zelite li upisati $valueForInput na poziciji $positionOfItemClickedInRecycler ?"
    }
    private fun getTextForItemsFromOneToSix() : String {
        val rowIndex = positionOfItemClickedInRecycler / 6
            for (member in diceRolled)
                if (member == rowIndex + 1)
                    valueForInput += rowIndex + 1

        return "Zelite li upisati $valueForInput na poziciji $positionOfItemClickedInRecycler ?"
        }

    override fun disposeOfObservers() {
        compositeDisposable.dispose()
    }


}