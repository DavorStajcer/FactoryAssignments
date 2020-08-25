package com.example.autofillgridlayoutmanagerapplication.displaying_saved_games

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.autofillgridlayoutmanagerapplication.database.entities_and_data_classes.GameStat
import com.example.autofillgridlayoutmanagerapplication.database.GamesPlayedDatabase
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.filling_yamb_ticket.ListOfItemsModifier
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.recyclerAdapter.ItemInGame
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.Adapters
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.FloatingActionButtton
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.IHasObservers
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.IViewModelForDisplayingYambTicket
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers



class DisplayingSavedGamesViewModel(val database: GamesPlayedDatabase) : ViewModel(), IViewModelForDisplayingYambTicket, IHasObservers {

    private val listOfGames_ : MutableLiveData<List<GameStat>> = MutableLiveData()
    val listOfGames : LiveData<List<GameStat>>
        get() = listOfGames_

    private val listOfItemsInRecyclerForDisplaying_ : MutableLiveData<List<ItemInGame>> = MutableLiveData()
    val listOfItemsInRecyclerForDisplaying : LiveData<List<ItemInGame>>
        get() = listOfItemsInRecyclerForDisplaying_

    private val textAboveRecycler_ : MutableLiveData<String> = MutableLiveData("PAST GAMES")
    val textAboveRecycler : LiveData<String>
        get() = textAboveRecycler_

    private val currentAdapter_ : MutableLiveData<Adapters> = MutableLiveData(Adapters.GAME_STATS)
    val currentAdapter : LiveData<Adapters>
        get() = currentAdapter_

    private val floatingActionButton_ : MutableLiveData<FloatingActionButtton> = MutableLiveData(FloatingActionButtton.DISABLED)
    val floatingActionButton : LiveData<FloatingActionButtton>
        get() = floatingActionButton_

    private val isDatabaseEmpty_ : MutableLiveData<Boolean> = MutableLiveData(true)
    val isDatabaseEmpty : LiveData<Boolean>
        get() = isDatabaseEmpty_


    private var compositeDisposable : CompositeDisposable = CompositeDisposable()
    private var gameIdForChangingTextFromFragment : Long = 0


    fun getGameStatsFromDatabase(){
        compositeDisposable.add(
            database.getGameStatsDao().getAllGameStats()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(){
                if(it.isNullOrEmpty())
                    this.isDatabaseEmpty_.value = true
                else{
                    this.isDatabaseEmpty_.value = false
                    this.listOfGames_.value = it
                }
            }
        )

    }
    fun showClickedGame(gameId : Long) {
        changeListOfItemsForAdapter(gameId)
        changeTextAboveRecycler()
        changeAdapter()
    }
    fun changeAdapter(){
       if(currentAdapter_.value == Adapters.GAME_STATS)
           currentAdapter_.value = Adapters.YAMB_GAME
        else
           currentAdapter_.value = Adapters.GAME_STATS
    }
    fun changeFloatingActionButtonState(state : FloatingActionButtton){
            floatingActionButton_.value = state
    }
    fun changeTextAboveRecycler() {
        if(currentAdapter.value == Adapters.GAME_STATS)
            textAboveRecycler_.value = "PAST GAMES"
        else{
                compositeDisposable.add(
                    database.getGameStatsDao().getGameStat(gameIdForChangingTextFromFragment)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(){
                        textAboveRecycler_.value = "Date: ${it.date}    Points: ${it.totalPoints}"
                    }
                )
        }
    }
    private fun changeListOfItemsForAdapter(gameId: Long){
        compositeDisposable.add(
            database.getGameStatsDao().getGameStatsWithCorrespondingColumns(gameId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    this.listOfItemsInRecyclerForDisplaying_.value = ListOfItemsModifier.generateListForRecyclerFromColumnsInDatabase(it.columns)
                }){
                    Log.i("adapter","ERROR ----------------------> ${it.message}")
                }
        )

    }

    override fun changeIsPopUpDialogEnabledState(position: Int,clickable : Boolean) {
        //do nothing when clicked
    }

    override fun disposeOfObservers() {
        compositeDisposable.dispose()
    }


}