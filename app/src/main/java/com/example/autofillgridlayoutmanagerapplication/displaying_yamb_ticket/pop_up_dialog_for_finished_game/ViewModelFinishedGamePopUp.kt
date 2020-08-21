package com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.pop_up_dialog_for_finished_game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.autofillgridlayoutmanagerapplication.databinding.PopUpFinishedGameDataForBinding

enum class ContinueGame(var saveGame : Boolean){
    CONTINUE(false),DONT_CONTINUE(false)
}

class FinishedGamePopUpViewModel : ViewModel() {

    private val continueNextGame_ : MutableLiveData<ContinueGame> = MutableLiveData(ContinueGame.DONT_CONTINUE)
    val continueNextGame : LiveData<ContinueGame>
        get() = continueNextGame_

    private val dataForBinding_ : MutableLiveData<PopUpFinishedGameDataForBinding> = MutableLiveData()
    val dataForBinding : LiveData<PopUpFinishedGameDataForBinding>
        get() = dataForBinding_


    fun changeContinueGameState(){
        if(continueNextGame_.value == ContinueGame.DONT_CONTINUE)
            continueNextGame_.value = ContinueGame.CONTINUE
        else
            continueNextGame_.value = ContinueGame.DONT_CONTINUE
    }

    fun chnageDataForBinding(totalPoints : Int){
        dataForBinding_.value =
            PopUpFinishedGameDataForBinding(totalPointsText = "Total points: $totalPoints")
    }


}