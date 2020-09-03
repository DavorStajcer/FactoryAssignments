package com.example.autofillgridlayoutmanagerapplication.displaying_saved_games

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.autofillgridlayoutmanagerapplication.R
import com.example.autofillgridlayoutmanagerapplication.database.entities_and_data_classes.GameStat
import com.example.autofillgridlayoutmanagerapplication.database.GamesPlayedDatabase
import com.example.autofillgridlayoutmanagerapplication.list_and_game_data_modifiers.ListOfItemsModifier
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.filling_yamb_ticket.recyclerAdapter.ItemInGame
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.Adapters
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.IHasObservers
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.IViewModelForDisplayingYambTicket
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlin.math.roundToInt


class DisplayingSavedGamesViewModel(val context: Context) : ViewModel(), IViewModelForDisplayingYambTicket, IHasObservers {

    private val listOfGames_ : MutableLiveData<List<GameStat>> = MutableLiveData()
    val listOfPlayedGames : LiveData<List<GameStat>>
        get() = listOfGames_

    private val listOfItemsInRecyclerForDisplaying_ : MutableLiveData<List<ItemInGame>> = MutableLiveData()
    val listOfItemsInChosenGame : LiveData<List<ItemInGame>>
        get() = listOfItemsInRecyclerForDisplaying_

    private val textAboveRecycler_ : MutableLiveData<String> = MutableLiveData("PAST GAMES")
    val textAbouveDisplayedGame : LiveData<String>
        get() = textAboveRecycler_

    private val currentAdapter_ : MutableLiveData<Adapters> = MutableLiveData(Adapters.GAME_STATS)
    val currentAdapter : LiveData<Adapters>
        get() = currentAdapter_

    private val floatingActionButton_ : MutableLiveData<Boolean> = MutableLiveData(true)
    val floatingActionButton : LiveData<Boolean>
        get() = floatingActionButton_

    private val isDatabaseEmpty_ : MutableLiveData<Boolean> = MutableLiveData()
    val isDatabaseEmpty : LiveData<Boolean>
        get() = isDatabaseEmpty_


    private var compositeDisposable : CompositeDisposable = CompositeDisposable()
    private var gameIdForChangingTextFromFragment : Long = 0


    init {
        getInitialGameStatsFromDatabase()
    }

    private fun getInitialGameStatsFromDatabase(){
        compositeDisposable.add(
            GamesPlayedDatabase.getInstanceOfDatabase(context).getGameStatsDao().getAllGameStats()
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
        chnageListOfItemsForDisplayingGame(gameId)
        //changeTextAboveRecycler()
        changeAdapter()
    }
    fun changeAdapter(){
       if(currentAdapter_.value == Adapters.GAME_STATS)
           currentAdapter_.value = Adapters.YAMB_GAME
        else
           currentAdapter_.value = Adapters.GAME_STATS
    }
    fun changeFloatingActionButtonState(){
            floatingActionButton_.value = !floatingActionButton_.value!!
    }
    fun changeTextAboveRecycler() {
        if(currentAdapter.value == Adapters.GAME_STATS){
            Log.i("changeText","changing text to PAST GAMES")
            textAboveRecycler_.value = context.getString(R.string.past_games_text)
        }
        else{
            Log.i("changeText","changing text to points and dates")
                compositeDisposable.add(
                    GamesPlayedDatabase.getInstanceOfDatabase(context).getGameStatsDao().getGameStat(gameIdForChangingTextFromFragment)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.i("changeText","changing text to points and dates TOTALY")
                        //textAboveRecycler_.value = "Date: ${it.date}    Points: ${it.totalPoints}"
                        val totalPoints : String = it.totalPoints.toString()
                        textAboveRecycler_.value = context.getString(R.string.game_stat_text,it.date,totalPoints)
                    }){
                        throw it
                    }
                )
        }
    }
    private fun chnageListOfItemsForDisplayingGame(gameId: Long){
        gameIdForChangingTextFromFragment = gameId
        compositeDisposable.add(
            GamesPlayedDatabase.getInstanceOfDatabase(context).getGameStatsDao().getGameStatsWithCorrespondingColumns(gameId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    this.listOfItemsInRecyclerForDisplaying_.value = ListOfItemsModifier.generateListForRecyclerFromColumnsInDatabase(it.columns)
                }){
                    throw it
                }
        )

    }
    override fun disposeOfObservers() {
        compositeDisposable.dispose()
    }

    override fun reactOnItemClicked(position: Int, clickable: Boolean) {
        TODO("Not yet implemented")
    }


}