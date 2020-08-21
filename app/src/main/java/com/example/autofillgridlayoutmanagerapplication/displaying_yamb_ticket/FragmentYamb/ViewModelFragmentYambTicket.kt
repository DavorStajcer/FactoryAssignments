package com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.FragmentYamb

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.autofillgridlayoutmanagerapplication.database.*
import com.example.autofillgridlayoutmanagerapplication.database.entities_and_data_classes.ColumnInYamb
import com.example.autofillgridlayoutmanagerapplication.database.entities_and_data_classes.Cubes
import com.example.autofillgridlayoutmanagerapplication.database.entities_and_data_classes.DataAboutRolledCubes
import com.example.autofillgridlayoutmanagerapplication.database.entities_and_data_classes.GameStat
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.recyclerAdapter.ItemInRecycler
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.IHasObservers
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.IViewModelForDisplayingYambTicket
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

enum class RecyclerAndFloatinActionButton{
    ENABLED,DISABLED
}
enum class Fragments(var buttonText : String){
    FRAGMENT_CUBES("YAMB"),FRAGMENT_YAMB("CUBES"),FRAGMENT_PAST_GAMES("PAST GAMES")
}




class ViewModelFragmentYambTicket(val database  :GamesPlayedDatabase) : ViewModel() , IViewModelForDisplayingYambTicket, IHasObservers {

    var diceRolled = listOf<Int>()


    private val aheadCall_ : MutableLiveData<Boolean> = MutableLiveData()
    val aheadCall : LiveData<Boolean>
        get() = aheadCall_

    private val  isPopUpForEnteringValuesEnabled_ : MutableLiveData<Boolean> = MutableLiveData(false)
    val isPopUpForEnteringValuesEnabled : LiveData<Boolean>
        get()= isPopUpForEnteringValuesEnabled_

    private val  areDicesRolledTwice_ : MutableLiveData<Boolean> = MutableLiveData()
    val areDicesRolledTwice : LiveData<Boolean>
        get()= areDicesRolledTwice_

    private val itemsInRecycler_ : MutableLiveData<List<ItemInRecycler>> = MutableLiveData()
    val itemsInRecycler : LiveData<List<ItemInRecycler>>
        get() = itemsInRecycler_

    private val isSendingPositionOfItemClickedToPopUpEnabled_ : MutableLiveData<Boolean> = MutableLiveData(false)
    val isSendingPositionOfItemClickedToPopUpEnabled : LiveData<Boolean>
        get() = isSendingPositionOfItemClickedToPopUpEnabled_

    private val isSendingTotalPointsToFinishedGamePopUpEnabled_: MutableLiveData<Boolean> = MutableLiveData(false)
    val isSendingTotalPointsToFinishedGamePopUpEnabled : LiveData<Boolean>
        get() = isSendingTotalPointsToFinishedGamePopUpEnabled_

    private val finishedGame_ = MutableLiveData<Boolean>(false)
    val  finishGameState : LiveData<Boolean>
        get() = finishedGame_

    private val totalPoints_ = MutableLiveData<Int>()
    val  totalPoints : LiveData<Int>
        get() = totalPoints_

    private val showToastForGameSaved_ = MutableLiveData<Boolean>(false)
    val showToastForGameSaved : LiveData<Boolean>
        get() = showToastForGameSaved_

    private val onClickItemPosition_ : MutableLiveData<Int> = MutableLiveData()
    val onClickItemPosition : LiveData<Int>
        get() = onClickItemPosition_


    private val compositeDisposable = CompositeDisposable()
    private var positioOfLastItemClickedTemp = 0

    init {
        generateStartingItems()

       // generateStartingTestItems()   ... generira listu koja ostavlja samo 2 polja nepopunjena, tako mogu lagano testirati kraj igre
    }

    fun setupObserverForDataAboutGame(){
        compositeDisposable.add(
            database.getDataAboutRolledCubesDao().getData(1)
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
       compositeDisposable.add( Completable.fromAction {

           val calendar = Calendar.getInstance()
           val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.ITALY)
           val date = dateFormat.format(calendar.time)
           val totalPoints = ItemListAndStatsGenerator.getTotalPoints()

           val currentInsertedGameId =
               database.getGameStatsDao().insertGameStats(GameStat(0, date, totalPoints)) //id is autogenerated
           val mapOfPlayedColumns = ItemListAndStatsGenerator.getMapOfColumnsFromListOfItems() //turn list into map of columns

           val listOfObjectsRepresentingColumns = mutableListOf<ColumnInYamb>()
           fillListWithFourObjectsWhoEachRepresentOneColumn(listOfObjectsRepresentingColumns, mapOfPlayedColumns,currentInsertedGameId)
           database.getColumnDao().insertColumns(
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
                    showToastMessage()
                }
            ){
                throw  it
            }
       )


    }
    fun changeItemsWhenAheadCallIsCalled(){
        this.itemsInRecycler_.value = ItemListAndStatsGenerator.getAheadCallPressedList()
    }
    fun changeItemsWhenPopUpForEnteringValuesCloses(valueOfItemPicked: Int){
        this.itemsInRecycler_.value = ItemListAndStatsGenerator.getNewList(onClickItemPosition.value!!, valueOfItemPicked)
        if(ItemListAndStatsGenerator.isGameFinished())
            changeIsGameFinishedState()
        enableButtonForRollingDicesAndFreezeRecycler()
    }
    fun unFreezeAllItems(){
        this.itemsInRecycler_.value = ItemListAndStatsGenerator.getUnfreezedList()
    }
    fun changeSendingPositionOfItemClickedToPopUpForEnteringValuesState(){
        isSendingPositionOfItemClickedToPopUpEnabled_.value = !isSendingPositionOfItemClickedToPopUpEnabled_.value!!
    }
    fun changeSendingTotalPointsToFinishedGamePopUpState(){
        isSendingTotalPointsToFinishedGamePopUpEnabled_.value = !isSendingTotalPointsToFinishedGamePopUpEnabled_.value!!
    }
    fun resetAllItems(){
        changeIsGameFinishedState()
        this.generateStartingItems()
        freezeAllItems()
    }
    fun changePositionOfLastItemClicked(){
        onClickItemPosition_.value = positioOfLastItemClickedTemp
    }
    private fun showToastMessage(){
        this.showToastForGameSaved_.value = true
        this.showToastForGameSaved_.value = false
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
        finishedGame_.value =  !finishedGame_.value!!
    }

    fun changeTotalPoints(){
        this.totalPoints_.value = ItemListAndStatsGenerator.getTotalPoints()
    }
    private fun enableButtonForRollingDicesAndFreezeRecycler(){
        compositeDisposable.add(
            Completable.fromAction {
                database.getDataAboutRolledCubesDao().insertData(
                        DataAboutRolledCubes(
                            dataRolledCubesId = 1,
                            cubes = Cubes(1, 1, 1, 1, 1, 1),
                            aheadCall = false,
                            isRecyclerFrozen = true,
                            enableButtonForRollingDices = true
                        )
                    )
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(){
                    freezeAllItems()
                }
        )
    }
    private fun generateStartingItems(){
        compositeDisposable.add(
            Single.just(ItemListAndStatsGenerator.generateStartingItems())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    itemsInRecycler_.value = it
                    freezeAllItems()
                }){
                    throw it
                }
        )
    }
    private fun freezeAllItems(){ //make them unclickable
        this.itemsInRecycler_.value = ItemListAndStatsGenerator.getFreezedList()
    }

    //za generiranje pocetnog listica pomocu kojeg testiram kraj igre
  /*  private fun generateStartingTestItems() {
      compositeDisposable.add(
          Single.just(ItemListAndStatsGenerator.generateStartingTestItems())
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe({
                  itemsInRecycler_.value = it
                  freezeAllItems()
              }){
                  throw it
              }
      )
    }*/
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
    override fun changeIsPopUpDialogEnabledState(position: Int,clickable : Boolean){
        if(clickable){
            positioOfLastItemClickedTemp = position
            isPopUpForEnteringValuesEnabled_.value = true
            isPopUpForEnteringValuesEnabled_.value = false
        }

    }
    override fun disposeOfObservers(){
        compositeDisposable.dispose()
    }

}