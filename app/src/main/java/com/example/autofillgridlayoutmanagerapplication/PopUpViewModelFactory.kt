package com.example.autofillgridlayoutmanagerapplication


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class PopUpViewModelFactory(val diceRolled : ArrayList<Int>, val position : Int, val row : Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PopUpWhenClickedDialogViewModel::class.java))
            return PopUpWhenClickedDialogViewModel(diceRolled,position,row) as T
        throw IllegalArgumentException("Class does not extend the ViewModel class.")
    }
}