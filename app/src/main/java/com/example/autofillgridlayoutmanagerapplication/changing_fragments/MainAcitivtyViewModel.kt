package com.example.autofillgridlayoutmanagerapplication.changing_fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable

enum class Fragments(var buttonText : String){
    FRAGMENT_CUBES("YAMB"),FRAGMENT_YAMB("CUBES"),FRAGMENT_PAST_GAMES("PAST GAMES")
}

enum class FinishedScreen{
    DISPLAY,DONT_DISPLAY
}


class MainAcitivtyViewModel : ViewModel() {

    private val isButtonForChangingFragmentsEnabled_ = MutableLiveData<Boolean>(false)
    val isButtonForChangingFragmentsEnabled : LiveData<Boolean>
        get() = isButtonForChangingFragmentsEnabled_

    private val isButtonForViewingGamesPlayedEnabled_ = MutableLiveData<Boolean>(true)
    val isButtonForViewingGamesPlayedEnabled : LiveData<Boolean>
        get() = isButtonForViewingGamesPlayedEnabled_

    private val fragmentToDisplay__ : MutableLiveData<Fragments> = MutableLiveData(Fragments.FRAGMENT_CUBES)
    val fragmentToDisplay : LiveData<Fragments>
        get() = fragmentToDisplay__

    private val displayFinishScreen_ : MutableLiveData<FinishedScreen> = MutableLiveData(FinishedScreen.DONT_DISPLAY)
    val displayFinishScreen : LiveData<FinishedScreen>
        get() = displayFinishScreen_

    private val totalPointsText_ : MutableLiveData<String> = MutableLiveData()
    val totalPointsText : LiveData<String>
        get() = totalPointsText_

    private val fragmentToDisplayWithViewPastGamesButton_ : MutableLiveData<Fragments> = MutableLiveData(Fragments.FRAGMENT_CUBES)
    val fragmentToDisplayWithViewPastGamesButton : LiveData<Fragments>
        get() = fragmentToDisplayWithViewPastGamesButton_




    fun changeButtonForViewingGamesPlayedState(state : Boolean){
        isButtonForViewingGamesPlayedEnabled_.value = state
    }

    fun changeButtonForChangingFragmentsState(value : Boolean){
        Log.i("buttonFragments","viewModel -> ${value}")

        isButtonForChangingFragmentsEnabled_.value = value
    }

    fun changeFragmentsWithButtonForDisplayingYamb(){
        if(fragmentToDisplay__.value == Fragments.FRAGMENT_CUBES)
            fragmentToDisplay__.value = Fragments.FRAGMENT_YAMB
        else
            fragmentToDisplay__.value  = Fragments.FRAGMENT_CUBES
    }

    fun changeFragmentsWithButtonForPastGames(){
        if(fragmentToDisplayWithViewPastGamesButton_.value == Fragments.FRAGMENT_CUBES){
            Fragments.FRAGMENT_PAST_GAMES.buttonText = "BACK"
            fragmentToDisplayWithViewPastGamesButton_.value = Fragments.FRAGMENT_PAST_GAMES
        }
        else{
            Fragments.FRAGMENT_PAST_GAMES.buttonText = "PAST GAMES"
            fragmentToDisplayWithViewPastGamesButton_.value  = Fragments.FRAGMENT_CUBES
        }

    }

   fun changeDisplayFinishedScreenStatus(){
           if(displayFinishScreen_.value == FinishedScreen.DISPLAY)
                displayFinishScreen_.value = FinishedScreen.DONT_DISPLAY
           else{
               displayFinishScreen_.value = FinishedScreen.DISPLAY
           }

   }

    fun changeTotalPoints(totalPoints : Int){
        this.totalPointsText_.value = totalPoints.toString()
    }



}