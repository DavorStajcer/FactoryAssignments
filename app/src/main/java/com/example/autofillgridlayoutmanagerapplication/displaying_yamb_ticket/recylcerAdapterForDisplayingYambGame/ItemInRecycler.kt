package com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.recylcerAdapterForDisplayingYambGame

import androidx.databinding.BaseObservable


data class ItemInRecycler(open val layoutId : Int = 0, open var data : Any? = null, open var clickable: Boolean = false) : BaseObservable()