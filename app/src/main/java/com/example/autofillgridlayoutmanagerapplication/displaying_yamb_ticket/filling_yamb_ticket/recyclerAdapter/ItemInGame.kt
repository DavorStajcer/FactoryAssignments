package com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.filling_yamb_ticket.recyclerAdapter

import androidx.databinding.BaseObservable


data class ItemInGame(val layoutId : Int = 0, var data : Any? = null, var clickable: Boolean = false) : BaseObservable()