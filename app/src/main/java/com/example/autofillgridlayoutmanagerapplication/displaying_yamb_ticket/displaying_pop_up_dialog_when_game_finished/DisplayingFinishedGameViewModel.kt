package com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.displaying_pop_up_dialog_when_game_finished

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.autofillgridlayoutmanagerapplication.database.GamesPlayedDatabase
import com.example.autofillgridlayoutmanagerapplication.databinding.DisplayingPopUpDialogGameFinishedBinidngData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

enum class ContinueGame(var saveGame : Boolean){
    CONTINUE(false),DONT_CONTINUE(false)
}

class FinishedGamePopUpViewModel(val database: GamesPlayedDatabase) : ViewModel() {

    init {
        initializeTotalPointsObserver()
    }

    private val continueNextGame_ : MutableLiveData<ContinueGame> = MutableLiveData(ContinueGame.DONT_CONTINUE)
    val continueNextGame : LiveData<ContinueGame>
        get() = continueNextGame_

    private val dataForBinding_ : MutableLiveData<DisplayingPopUpDialogGameFinishedBinidngData> = MutableLiveData()
    val dataForBinding : LiveData<DisplayingPopUpDialogGameFinishedBinidngData>
        get() = dataForBinding_

    private lateinit var disposable : Disposable

    fun changeContinueGameState(){
        if(continueNextGame_.value == ContinueGame.DONT_CONTINUE)
            continueNextGame_.value = ContinueGame.CONTINUE
        else
            continueNextGame_.value = ContinueGame.DONT_CONTINUE
    }
    private fun  initializeTotalPointsObserver(){
        disposable = database.getPopUpsDataDao().getPopUpsData(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(){
                changeDataForBinding(it.totalPoints)
            }
    }
    private fun changeDataForBinding(totalPoints : Int){
        dataForBinding_.value = DisplayingPopUpDialogGameFinishedBinidngData(totalPointsText = "Total points: $totalPoints")
    }

}