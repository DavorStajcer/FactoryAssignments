package com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.autofillgridlayoutmanagerapplication.database.ColumnInYamb
import com.example.autofillgridlayoutmanagerapplication.database.GameStat
import com.example.autofillgridlayoutmanagerapplication.database.GamesPlayedDatabase
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.recylcer.ItemInRecycler
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.*
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*


class FragmentYambViewModel : ViewModel() {

    var diceRolled = listOf<Int>()
    var onClickItemPosition = 0

    private val aheadCall_ : MutableLiveData<AheadCall> = MutableLiveData()
    val aheadCall : LiveData<AheadCall>
        get() = aheadCall_

    private val  isPopUpEnabled_ : MutableLiveData<PopUpState> = MutableLiveData()
    val isPopUpEnabled : LiveData<PopUpState>
        get()= isPopUpEnabled_

    private val  isRecyclerEnabled_ : MutableLiveData<Recycler> = MutableLiveData(Recycler.ENABLED)
    val isRecyclerEnabled : LiveData<Recycler>
        get()= isRecyclerEnabled_

    private val itemsInRecycler_ : MutableLiveData<List<ItemInRecycler>> = MutableLiveData()
    val itemsInRecycler : LiveData<List<ItemInRecycler>>
        get() = itemsInRecycler_

    private val sendDataToPopUp_ : MutableLiveData<SendingDataToPopUp> = MutableLiveData()
    val sendDataToPopUp : LiveData<SendingDataToPopUp>
        get() = sendDataToPopUp_

    private val isButtonForChangingFragmentsEnabled_ : MutableLiveData<ButtonForChangingFragmentsState> = MutableLiveData()
    val isButtonForChangingFragmentsEnabled : LiveData<ButtonForChangingFragmentsState>
        get() = isButtonForChangingFragmentsEnabled_

    private val finishedGame_ = MutableLiveData<FinishGameState>(FinishGameState.NOT_FINISHED)
    val  finishGameState : LiveData<FinishGameState>
        get() = finishedGame_

    init {
       // generateStartingItems()
        generateStartingTestItems()
    }


    fun saveGame(context : Context){

        val databaseInstance = GamesPlayedDatabase.getInstanceOfDatabase(context)
        val  gameStatsDao = databaseInstance.getGameStatsDao()
        val  columnsDao = databaseInstance.getColumnDao()

        Log.i("tag","\n\n\n\n\n\n\n\n\n\nMAP OF ROWS : \n\n${ItemListAndStatsGenerator.getMapOfRows()}")



       val disposalbe = Completable.fromAction {

           val calendar = Calendar.getInstance()
           val dateFormat = SimpleDateFormat("MM/dd/yyyy");
           val date = dateFormat.format(calendar.getTime());
           val totalPoints = ItemListAndStatsGenerator.getTotalPoints()


           val currentInsertedGameId = gameStatsDao.insertGameStats(
               GameStat(0,date,totalPoints)
           )

           val mapOfPlayedRows = ItemListAndStatsGenerator.getMapOfRows()
           val listOfColumnsInYamb = mutableListOf<ColumnInYamb>()

           for((key,rowList) in mapOfPlayedRows){
               listOfColumnsInYamb.add(key, ColumnInYamb(
                   columnId = 0,
                   correspondingGameId = currentInsertedGameId,
                   one = rowList[0],
                   two = rowList[1],
                   three = rowList[2],
                   four = rowList[3],
                   five = rowList[4],
                   six = rowList[5],
                   sum_form_one_to_six = rowList[6],
                   max = rowList[7],
                   min = rowList[8],
                   max_min= rowList[9],
                   two_pairs = rowList[10],
                   straight = rowList[11],
                   full = rowList[12],
                   poker = rowList[13],
                   yamb = rowList[14],
                   sum_from_two_pairs_to_yamb = rowList[15]
               ))
           }

           Log.i("tag","COLUMNS : \n\n$listOfColumnsInYamb \n\n")



                     Log.i("tag","COLUMNS : \n\n$listOfColumnsInYamb \n\n")

                   columnsDao.insertColumns(
                         listOfColumnsInYamb[0],
                         listOfColumnsInYamb[1],
                         listOfColumnsInYamb[2],
                         listOfColumnsInYamb[3],
                         listOfColumnsInYamb[4]
                     )

       }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({Toast.makeText(context,"Game saved.", Toast.LENGTH_SHORT).show()}){
                Log.i("tag",it.message ?: "nema throwable message")
            }


    }




    fun changeItemsWhenAheadCallIsCalled(){
        this.itemsInRecycler_.value = ItemListAndStatsGenerator.getAheadCallPressedList()
    }

    fun changeItemsWhenPopUpForEnteringValuesCloses(valueOfItemPicked: Int){

        this.itemsInRecycler_.value = ItemListAndStatsGenerator.getNewList(onClickItemPosition,valueOfItemPicked)

        if(ItemListAndStatsGenerator.isGameFinished()){
            changeIsGameFinishedState()
            changeIsRecyclerScrollingAndClickingEnabled()
        }
        freezeAllItems()
    }

    fun changeIsGameFinishedState(){
        if(finishedGame_.value == FinishGameState.FINISHED)
            finishedGame_.value = FinishGameState.NOT_FINISHED
        else{
            FinishGameState.FINISHED.totalPoints = ItemListAndStatsGenerator.getTotalPoints()
            finishedGame_.value = FinishGameState.FINISHED
        }

    }

    fun unFreezeAllItems(){
        this.itemsInRecycler_.value = ItemListAndStatsGenerator.getUnfreezedList()
    }

    fun changeIsPopUpDialogEnabledState(position: Int){
        this.onClickItemPosition = position
        PopUpState.SHOW.position = position
        isPopUpEnabled_.value = PopUpState.SHOW
        isPopUpEnabled_.value = PopUpState.HIDE
    }

    fun enableSendingDataToPup(){
        SendingDataToPopUp.ENABLED.diceRolled = diceRolled
        SendingDataToPopUp.ENABLED.position = onClickItemPosition
        sendDataToPopUp_.value = SendingDataToPopUp.ENABLED
        sendDataToPopUp_.value = SendingDataToPopUp.DISABLED
    }

    fun changeAheadCall(aheadCall: Boolean){
        if(aheadCall)
            this.aheadCall_.value = AheadCall.CALLED
        else
            this.aheadCall_.value = AheadCall.NOT_CALLED
    }

    fun changeIsRecyclerScrollingAndClickingEnabled(){
        if(isRecyclerEnabled_.value == Recycler.ENABLED)
            isRecyclerEnabled_.value = Recycler.DISABLED
        else
            isRecyclerEnabled_.value = Recycler.ENABLED
    }

    fun resetAllItems(){
        this.generateStartingItems()
    }

    fun changeButtonForChangingFragmentsState(){
        if( isButtonForChangingFragmentsEnabled_.value == ButtonForChangingFragmentsState.ENABLED )
            isButtonForChangingFragmentsEnabled_.value = ButtonForChangingFragmentsState.DISABLED
        else{
            if(finishGameState.value == FinishGameState.NOT_FINISHED)
                isButtonForChangingFragmentsEnabled_.value = ButtonForChangingFragmentsState.ENABLED
        }
    }

    private fun generateStartingItems(){
      this.itemsInRecycler_.value = ItemListAndStatsGenerator.generateStartingItems()
    }

    private fun freezeAllItems(){ //make them unclickable
        this.itemsInRecycler_.value = ItemListAndStatsGenerator.getFreezedList()
    }

    private fun generateStartingTestItems() {
        itemsInRecycler_.value = ItemListAndStatsGenerator.generateStartingTestItems()
    }

}