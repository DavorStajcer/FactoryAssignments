package com.example.autofillgridlayoutmanagerapplication.pastGames

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.autofillgridlayoutmanagerapplication.database.GameStat
import com.example.autofillgridlayoutmanagerapplication.database.GamesPlayedDatabase
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.ItemListAndStatsGenerator
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.recylcer.ItemInRecycler
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.Adapters
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PastGamesViewModel : ViewModel() {

    private val listOfGames_ : MutableLiveData<List<GameStat>> = MutableLiveData()
    val listOfGames : LiveData<List<GameStat>>
        get() = listOfGames_

    private val listOfItemsInRecyclerForDisplaying_ : MutableLiveData<List<ItemInRecycler>> = MutableLiveData()
    val listOfItemsInRecyclerForDisplaying : LiveData<List<ItemInRecycler>>
        get() = listOfItemsInRecyclerForDisplaying_

    private val textAboveRecycler_ : MutableLiveData<String> = MutableLiveData("PAST GAMES")
    val textAboveRecycler : LiveData<String>
        get() = textAboveRecycler_

    private val currentAdapter_ : MutableLiveData<Adapters> = MutableLiveData(Adapters.GAME_STATS)
    val currentAdapter : LiveData<Adapters>
        get() = currentAdapter_

    private var compositeDisposable : CompositeDisposable = CompositeDisposable()
    private var database : GamesPlayedDatabase? = null

    var gameIdForChangingTextFromFragment : Long = 0


    fun getGameStatsFromDatabase(context : Context){

        database = GamesPlayedDatabase.getInstanceOfDatabase(context)

        compositeDisposable.add(
            database!!.getGameStatsDao().getAllGameStats()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(){
                this.listOfGames_.value = it
            }
        )

    }

    fun showClickedGame(gameId : Long) {
        this.gameIdForChangingTextFromFragment = gameId
        changeListOfItemsForAdapter(gameId)
        changeTextAboveRecycler("SELECTED GAME STATS")
        changeAdapter(Adapters.YAMB_GAME)
    }

    fun changeAdapter(adapter : Adapters){
       currentAdapter_.value = adapter
    }

    fun changeTextAboveRecycler(text : String){

        if(text == "PAST GAMES")
            textAboveRecycler_.value = "PAST GAMES"
        else{
            compositeDisposable.add(  database!!.getGameStatsDao().getGameStat(gameIdForChangingTextFromFragment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(){
                    this.textAboveRecycler_.value = "Date: ${it.date}   Points: ${it.totalPoints}"
                }
            )

        }

    }

    private fun changeListOfItemsForAdapter(gameId: Long){

        compositeDisposable.add(

            database!!.getGameStatsDao().getGameStatsWithCorrespondingColumns(gameId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    this.listOfItemsInRecyclerForDisplaying_.value = ItemListAndStatsGenerator.getListFromColumnsInDatabase(it.columns)
                }){
                    Log.i("adapter","ERROR ----------------------> ${it.message}")
                }

        )

    }

    fun doNothingWhenRecyclcerAdapterIsClicked(a : Int) :Unit {
            //Ova metoda postoji samo zato što koristim adapter za recycler od yamb listica,a u konstruktor
            //se takvom adapteru predaje funkcija ovakvog oblika , koja se poziva na klik elementa u recycleru

            //pošto vamo nemam mogućnost klika, treba mi ovakva "dummy" funkcija, koja ne readi nista
    }

}