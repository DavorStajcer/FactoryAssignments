package com.example.autofillgridlayoutmanagerapplication.changing_fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.IllegalStateException

enum class Fragments(val buttonText : String){
    FRAGMENT_CUBES("YAMB"),FRAGMENT_YAMB("CUBES")
}

class MainAcitivtyViewModel : ViewModel() {

    private val isButtonEnabled_ = MutableLiveData<Boolean>(false)
    val isButtonEnabled : LiveData<Boolean>
        get() = isButtonEnabled_
    private val fragmentToDisplay__ : MutableLiveData<Fragments> = MutableLiveData(Fragments.FRAGMENT_CUBES)
    val fragmentToDisplay : LiveData<Fragments>
        get() = fragmentToDisplay__

    fun changeButtonForChangingFragmentsState(value : Boolean){
        isButtonEnabled_.value = value
    }

    fun changeFragments(){
        if(fragmentToDisplay__.value == Fragments.FRAGMENT_CUBES)
            fragmentToDisplay__.value = Fragments.FRAGMENT_YAMB
        else
            fragmentToDisplay__.value  = Fragments.FRAGMENT_CUBES
    }

}