package com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.recylcerAdapterForDisplayingYambGame

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager

import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max

class AutoFillGridLayoutManager(context: Context,var columnWidth : Int, var columnWidthChanged : Boolean = true) : GridLayoutManager(context,columnWidth) {


    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        Log.i("WIDTH", "SPAN ( TOTAL/COLUMNWIDHT = $columnWidth")
        if(columnWidthChanged && columnWidth > 0) {
            val totalSpace: Int = if (orientation == VERTICAL)
                width - paddingLeft - paddingRight
            else
                height - paddingTop - paddingBottom

            spanCount = max(1, totalSpace/columnWidth)
            Log.i("WIDTH", "SPAN ( TOTAL/COLUMNWIDHT = $spanCount")
            columnWidthChanged = false
        }
        Log.i("WIDTH", width.toString())
        super.onLayoutChildren(recycler, state)
    }

}