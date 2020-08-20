package com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.pop_up_dialog_for_inserting_values

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.autofillgridlayoutmanagerapplication.R
import com.example.autofillgridlayoutmanagerapplication.database.EntitiesAndDataCalsses.Cubes
import com.example.autofillgridlayoutmanagerapplication.database.GamesPlayedDatabase
import com.example.autofillgridlayoutmanagerapplication.databinding.PopUpForInsertingValuesDataForBinding
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.FragmentYamb.RowIndexOfResultElements
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


enum class SendDataOfItemChosen(var positionOfItemChosen : Int, var valueForInput : Int){
    ENABLED(0,0),DISABLED(0,0)
}

class PopUpWhenClickedDialogViewModel (
)  : ViewModel(){

    private val sendingDataOfItemChosen_ = MutableLiveData<SendDataOfItemChosen>()
    val sendingDataOfItemChosen : LiveData<SendDataOfItemChosen>
        get() = sendingDataOfItemChosen_

    private val dataForBinding_ = MutableLiveData<PopUpForInsertingValuesDataForBinding>()
    val dataForBinding : LiveData<PopUpForInsertingValuesDataForBinding>
        get() = dataForBinding_

    private val recivePositionOfPickedItem_ = MutableLiveData<Boolean>(false)
    val recivePositionOfPickedItem : LiveData<Boolean>
        get() = recivePositionOfPickedItem_


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




    fun setDiceRolleDatabaseObserver(database: GamesPlayedDatabase){
        compositeDisposable.add(

            database.getDataAboutRolledCubesDao().getData(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(){
                    this.diceRolled = generateDiceRolledWithDataFromDatabase(it.cubes)
                    changeShouldPopUpRecivePoistionOfPickedItem()
                }

        )
    }
    fun setTextForDisplay(positionOfItemClickedInRecycler: Int) {
        this.positionOfItemClickedInRecycler = positionOfItemClickedInRecycler
        this.valueForInput = 0

        val textForDisplay = getTextForDisplay()
        val pictureSourceList : List<Int> = mapNumbersRolledToPicutresForDisplaying()
        changeDataforBinding(textForDisplay,pictureSourceList)
    }
    fun sendDataOfItemPickedBack(){
        SendDataOfItemChosen.ENABLED.positionOfItemChosen = positionOfItemClickedInRecycler
        SendDataOfItemChosen.ENABLED.valueForInput = valueForInput
        sendingDataOfItemChosen_.value = SendDataOfItemChosen.ENABLED
        sendingDataOfItemChosen_.value = SendDataOfItemChosen.DISABLED

    }
    fun changeShouldPopUpRecivePoistionOfPickedItem(){
        this.recivePositionOfPickedItem_.value = !recivePositionOfPickedItem_.value!!
    }

    private fun changeDataforBinding(textForDisplay : String, pictureSourceList : List<Int>){
        dataForBinding_.value = PopUpForInsertingValuesDataForBinding(pictureSourceList[0], pictureSourceList[1], pictureSourceList[2], pictureSourceList[3], pictureSourceList[4], pictureSourceList[5], textForDisplay)
    }
    private fun mapNumbersRolledToPicutresForDisplaying() : List<Int>{
        val tempPictureSourceList = mutableListOf<Int>()
        for(numberRolled in diceRolled){
            when(numberRolled){
                1 -> tempPictureSourceList.add(R.drawable.cube1)
                2 -> tempPictureSourceList.add(R.drawable.cube2)
                3 -> tempPictureSourceList.add(R.drawable.cube3)
                4 -> tempPictureSourceList.add(R.drawable.cube4)
                5 -> tempPictureSourceList.add(R.drawable.cube5)
                6 -> tempPictureSourceList.add(R.drawable.cube6)
                else -> throw IllegalArgumentException("ViewModelPopUpItemWhenClicked -> diceRolled list containes numbers diffrent than numbers from 1-6 !? This is madness !..Madness ?..This ! Is ! Andorid Studio !")
            }
        }
        return tempPictureSourceList
    }
    private fun generateDiceRolledWithDataFromDatabase(cubesRolled : Cubes) : List<Int>{
        val tempDiceRolledList = mutableListOf<Int>()

        tempDiceRolledList.add(cubesRolled.cubeOne)
        tempDiceRolledList.add(cubesRolled.cubeTwo)
        tempDiceRolledList.add(cubesRolled.cubeThree)
        tempDiceRolledList.add(cubesRolled.cubeFour)
        tempDiceRolledList.add(cubesRolled.cubeFive)
        tempDiceRolledList.add(cubesRolled.cubeSix)

        return tempDiceRolledList
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










}