package com.example.autofillgridlayoutmanagerapplication.changing_fragments

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

enum class Fragments(val buttonText : String){
    FRAGMENT_CUBES("YAMB"),FRAGMENT_YAMB("CUBES")
}

enum class FinishedScreen{
    DISPLAY,DONT_DISPLAY
}


class MainAcitivtyViewModel : ViewModel() {

    private val isButtonEnabled_ = MutableLiveData<Boolean>(false)
    val isButtonEnabled : LiveData<Boolean>
        get() = isButtonEnabled_

    private val fragmentToDisplay__ : MutableLiveData<Fragments> = MutableLiveData(Fragments.FRAGMENT_CUBES)
    val fragmentToDisplay : LiveData<Fragments>
        get() = fragmentToDisplay__

    private val displayFinishScreen_ : MutableLiveData<FinishedScreen> = MutableLiveData(FinishedScreen.DONT_DISPLAY)
    val displayFinishScreen : LiveData<FinishedScreen>
        get() = displayFinishScreen_

    private val totalPointsText_ : MutableLiveData<String> = MutableLiveData()
    val totalPointsText : LiveData<String>
        get() = totalPointsText_



    fun changeButtonForChangingFragmentsState(value : Boolean){
        Log.i("buttonFragments","viewModel -> ${value}")

        isButtonEnabled_.value = value
    }

    fun changeFragments(){
        if(fragmentToDisplay__.value == Fragments.FRAGMENT_CUBES)
            fragmentToDisplay__.value = Fragments.FRAGMENT_YAMB
        else
            fragmentToDisplay__.value  = Fragments.FRAGMENT_CUBES
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