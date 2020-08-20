package com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.FragmentYamb

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.util.TableInfo
import com.example.autofillgridlayoutmanagerapplication.database.*
import com.example.autofillgridlayoutmanagerapplication.database.EntitiesAndDataCalsses.ColumnInYamb
import com.example.autofillgridlayoutmanagerapplication.database.EntitiesAndDataCalsses.Cubes
import com.example.autofillgridlayoutmanagerapplication.database.EntitiesAndDataCalsses.DataAboutRolledCubes
import com.example.autofillgridlayoutmanagerapplication.database.EntitiesAndDataCalsses.GameStat
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.recylcerAdapterForDisplayingYambGame.ItemInRecycler
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

enum class PopUpState(){
    SHOW,HIDE
}
enum class FinishGameState(){
    FINISHED,NOT_FINISHED
}
enum class SendingDataToPopUp(var diceRolled : List<Int>,var position: Int){
    ENABLED(listOf(),0),DISABLED(listOf(),0)
}
enum class SendingDataToFinishedGamePopUp(var totalPoints : Int){
    ENABLED(0),DISABLED(0)
}
enum class RecyclerAndFloatinActionButton{
    ENABLED,DISABLED
}
enum class Adapters(){
    GAME_STATS(),YAMB_GAME()
}
enum class Fragments(var buttonText : String){
    FRAGMENT_CUBES("YAMB"),FRAGMENT_YAMB("CUBES"),FRAGMENT_PAST_GAMES("PAST GAMES")
}




class ViewModel_FragmentYambTicket : ViewModel() {

    var diceRolled = listOf<Int>()
    var onClickItemPosition = 0

    private val aheadCall_ : MutableLiveData<Boolean> = MutableLiveData()
    val aheadCall : LiveData<Boolean>
        get() = aheadCall_

    private val  isPopUpEnabled_ : MutableLiveData<PopUpState> = MutableLiveData(PopUpState.HIDE)
    val isPopUpForEnteringValuesEnabled : LiveData<PopUpState>
        get()= isPopUpEnabled_

    private val  areDicesRolledTwice_ : MutableLiveData<Boolean> = MutableLiveData(false)
    val areDicesRolledTwice : LiveData<Boolean>
        get()= areDicesRolledTwice_

    private val itemsInRecycler_ : MutableLiveData<List<ItemInRecycler>> = MutableLiveData()
    val itemsInRecycler : LiveData<List<ItemInRecycler>>
        get() = itemsInRecycler_

    private val sendDataToPopUpForInsertingValues_ : MutableLiveData<SendingDataToPopUp> = MutableLiveData()
    val sendDataToPopUpForInsertingValues : LiveData<SendingDataToPopUp>
        get() = sendDataToPopUpForInsertingValues_

    private val sendDataToPopUpForFinishedGame_ : MutableLiveData<SendingDataToFinishedGamePopUp> = MutableLiveData()
    val sendDataToPopUpForFinishedGame : LiveData<SendingDataToFinishedGamePopUp>
        get() = sendDataToPopUpForFinishedGame_

    private val finishedGame_ = MutableLiveData<FinishGameState>(FinishGameState.NOT_FINISHED)
    val  finishGameState : LiveData<FinishGameState>
        get() = finishedGame_



    private var context : Context? = null
    private val compositeDisposable = CompositeDisposable()



    init {
        //generateStartingItems()
        generateStartingTestItems()
    }

    fun setupObserverForDataAboutGame(activityContext: Context){
        context = context ?: activityContext
        val database = GamesPlayedDatabase.getInstanceOfDatabase(context = context!!)

        compositeDisposable.add( database.getDataAboutRolledCubesDao().getData(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(){
                this.areDicesRolledTwice_.value = !it.isRecyclerFrozen
                if(!it.isRecyclerFrozen){
                    this.aheadCall_.value = it.aheadCall
                    this.diceRolled = generateDiceRolledWithDataFromDatabase(it.cubes)
                }
            }
        )

    }
    fun saveGame(){

        val databaseInstance = GamesPlayedDatabase.getInstanceOfDatabase(context!!)
        val  gameStatsDao = databaseInstance.getGameStatsDao()
        val  columnsDao = databaseInstance.getColumnDao()

       val disposalbe = Completable.fromAction {

           val calendar = Calendar.getInstance()
           val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.ITALY)
           val date = dateFormat.format(calendar.time)
           val totalPoints = ItemListAndStatsGenerator.getTotalPoints()

           val currentInsertedGameId = gameStatsDao.insertGameStats(GameStat(0, date, totalPoints))
           val mapOfPlayedColumns = ItemListAndStatsGenerator.getMapOfColumnsFromListOfItems()

           val listOfObjectsRepresentingColumns = mutableListOf<ColumnInYamb>()

           fillListWithFourObjectsWhoEachRepresentOneColumn(listOfObjectsRepresentingColumns, mapOfPlayedColumns,currentInsertedGameId)

            columnsDao.insertColumns(
                listOfObjectsRepresentingColumns[0],
                listOfObjectsRepresentingColumns[1],
                listOfObjectsRepresentingColumns[2],
                listOfObjectsRepresentingColumns[3],
                listOfObjectsRepresentingColumns[4]
            )

       }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    changeIsGameFinishedState()
                    this.generateStartingItems()
                    freezeAllItems()
                    Toast.makeText(context,"Game saved.", Toast.LENGTH_SHORT).show()
                }
            ){
                Log.i("tag",it.message ?: "nema throwable message")
            }


    }
    fun changeItemsWhenAheadCallIsCalled(){
        this.itemsInRecycler_.value =
            ItemListAndStatsGenerator.getAheadCallPressedList()
    }
    fun changeItemsWhenPopUpForEnteringValuesCloses(valueOfItemPicked: Int){

        this.itemsInRecycler_.value =
            ItemListAndStatsGenerator.getNewList(
                onClickItemPosition,
                valueOfItemPicked
            )


        if(ItemListAndStatsGenerator.isGameFinished())
            changeIsGameFinishedState()
        enableButtonForRollingDicesAndFreezeRecycler()


    }


    fun unFreezeAllItems(){
        this.itemsInRecycler_.value =
            ItemListAndStatsGenerator.getUnfreezedList()
    }
    fun changeIsPopUpDialogEnabledState(position: Int){
        this.onClickItemPosition = position
        isPopUpEnabled_.value = PopUpState.SHOW
        isPopUpEnabled_.value = PopUpState.HIDE
    }
    fun enableSendingDataToPopUpForInsertingValues(){
        SendingDataToPopUp.ENABLED.diceRolled = diceRolled
        SendingDataToPopUp.ENABLED.position = onClickItemPosition
        sendDataToPopUpForInsertingValues_.value = SendingDataToPopUp.ENABLED
        sendDataToPopUpForInsertingValues_.value = SendingDataToPopUp.DISABLED
    }
    fun enableSendingTotalPointsToFinishedGamePopUp(){
        sendDataToPopUpForFinishedGame_.value = SendingDataToFinishedGamePopUp.ENABLED
        sendDataToPopUpForFinishedGame_.value = SendingDataToFinishedGamePopUp.DISABLED
    }
    fun resetAllItems(){
        changeIsGameFinishedState()
        this.generateStartingItems()
        freezeAllItems()
    }

    private fun fillListWithFourObjectsWhoEachRepresentOneColumn(listOfObjectsRepresentingColumns : MutableList<ColumnInYamb>,mapOfPlayedColumns : Map<Int,List<Int?>>,currentInsertedGameId : Long){
        for((key,rowList) in mapOfPlayedColumns){
            listOfObjectsRepresentingColumns.add(key,
                ColumnInYamb(
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
                    max_min = rowList[9],
                    two_pairs = rowList[10],
                    straight = rowList[11],
                    full = rowList[12],
                    poker = rowList[13],
                    yamb = rowList[14],
                    sum_from_two_pairs_to_yamb = rowList[15]
                )
            )
        }
    }
    private fun changeIsGameFinishedState(){
        if(finishedGame_.value == FinishGameState.FINISHED){
            finishedGame_.value = FinishGameState.NOT_FINISHED
        }
        else{
            SendingDataToFinishedGamePopUp.ENABLED.totalPoints = ItemListAndStatsGenerator.getTotalPoints()
            finishedGame_.value = FinishGameState.FINISHED
        }
    }
    private fun enableButtonForRollingDicesAndFreezeRecycler(){

        val dao =GamesPlayedDatabase.getInstanceOfDatabase(context!!).getDataAboutRolledCubesDao()
        compositeDisposable.add(
            Completable.fromAction {
                Log.i("dataAboutCubes","Inserting new data")
                    dao.insertData(
                        DataAboutRolledCubes(
                            dataRolledCubesId = 1,
                            cubes = Cubes(
                                1,
                                1,
                                1,
                                1,
                                1,
                                1
                            ),
                            aheadCall = false,
                            isRecyclerFrozen = true,
                            enableButtonForRollingDices = true
                        )
                    )
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}){
                    Log.i("d","error message : ${it.message}")
                }
        )

 /*       compositeDisposable.add(
            Completable.fromAction {
                Log.i("dataAboutCubes","Saving that recycler should be frozen")
                dao.updateIsRecyclerFreezedState(1,true)
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}){
                    Log.i("tag","error message : ${it.message}")
                }

        )
*/
        freezeAllItems()
    }
    private fun generateStartingItems(){
      this.itemsInRecycler_.value =
          ItemListAndStatsGenerator.generateStartingItems()
    }
    private fun freezeAllItems(){ //make them unclickable
        Log.i("dataAboutCubes","Freezing items..")
        this.itemsInRecycler_.value =
            ItemListAndStatsGenerator.getFreezedList()
    }
    private fun generateStartingTestItems() {
        itemsInRecycler_.value =
            ItemListAndStatsGenerator.generateStartingTestItems()
        freezeAllItems()
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

}