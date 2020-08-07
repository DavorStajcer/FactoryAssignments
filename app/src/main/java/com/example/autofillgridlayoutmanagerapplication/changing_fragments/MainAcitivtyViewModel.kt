package com.example.autofillgridlayoutmanagerapplication.changing_fragments

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.IllegalStateException

class MainAcitivtyViewModel : ViewModel() {

    val isButtonEnabled = MutableLiveData<Boolean>(false)
    val changeFragmentToCubes = MutableLiveData<Int>(1)
    val changeFragmentToYamb = MutableLiveData<Int>()
    private var firstTimeChanging = true

    fun changeButtonForChangingFragmentsState(value : Boolean){
        isButtonEnabled.value = value
    }

    fun changeFragments(){

        if(firstTimeChanging){
            changeFragmentToYamb.value = 1
            firstTimeChanging = false
        }
        else{
            val fragmentYambValue = changeFragmentToYamb.value ?: throw IllegalStateException("change fragments mutable live data is null !?")
            val fragmentCubesValue = changeFragmentToCubes.value ?: throw IllegalStateException("change fragments mutable live data is null !?")

            Log.i("button","MmainAcitivtyViewModel, changeFragment,CUBES -> ${fragmentCubesValue}")
            Log.i("button","MmainAcitivtyViewModel, changeFragment,YAMB -> ${fragmentYambValue}")

            if(fragmentCubesValue == fragmentYambValue)
                changeFragmentToCubes.value = fragmentCubesValue + 1
            if(fragmentCubesValue > fragmentYambValue)
                changeFragmentToYamb.value = fragmentYambValue + 1
        }
    }
}