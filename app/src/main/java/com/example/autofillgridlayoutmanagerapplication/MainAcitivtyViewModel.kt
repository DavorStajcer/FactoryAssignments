package com.example.autofillgridlayoutmanagerapplication

import androidx.lifecycle.ViewModel

class MainAcitivtyViewModel : ViewModel() {

    var aheadCall : Boolean = false

    private val mutableListOfFirstColumn = mutableListOf<DataModel>(
        DataModel(R.layout.grid_layout_element_image,R.drawable.cube1),DataModel(R.layout.grid_layout_element_image,R.drawable.cube2),
        DataModel(R.layout.grid_layout_element_image,R.drawable.cube3),DataModel(R.layout.grid_layout_element_image,R.drawable.cube4),
        DataModel(R.layout.grid_layout_element_image,R.drawable.cube5),DataModel(R.layout.grid_layout_element_image,R.drawable.cube6),
        DataModel(R.layout.grid_layout_element_image,R.drawable.zbroj_od_jedan_do_deset),DataModel(R.layout.grid_layout_element_image,R.drawable.max),
        DataModel(R.layout.grid_layout_element_image,R.drawable.min),DataModel(R.layout.grid_layout_element_image,R.drawable.max_min),
        DataModel(R.layout.grid_layout_element_image,R.drawable.dva_para),DataModel(R.layout.grid_layout_element_image,R.drawable.straight),
        DataModel(R.layout.grid_layout_element_image,R.drawable.full),DataModel(R.layout.grid_layout_element_image,R.drawable.poker),
        DataModel(R.layout.grid_layout_element_image,R.drawable.yamb),DataModel(R.layout.grid_layout_element_image,R.drawable.zbroj_od_dva_para_do_yamba)
    )
    private val mutableListOfSecondColumns = mutableListOf<DataModel>(
        DataModel(R.layout.grid_layout_element_text_view,clickable = true),DataModel(R.layout.grid_layout_element_text_view),
        DataModel(R.layout.grid_layout_element_text_view),DataModel(R.layout.grid_layout_element_text_view),
        DataModel(R.layout.grid_layout_element_text_view),DataModel(R.layout.grid_layout_element_text_view),
        DataModel(R.layout.grid_layout_element_text_view),DataModel(R.layout.grid_layout_element_text_view),
        DataModel(R.layout.grid_layout_element_text_view),DataModel(R.layout.grid_layout_element_text_view),
        DataModel(R.layout.grid_layout_element_text_view),DataModel(R.layout.grid_layout_element_text_view),
        DataModel(R.layout.grid_layout_element_text_view),DataModel(R.layout.grid_layout_element_text_view),
        DataModel(R.layout.grid_layout_element_text_view),DataModel(R.layout.grid_layout_element_text_view)
    )
    private val mutableListOfThirdColumn= mutableListOf<DataModel>(
        DataModel(R.layout.grid_layout_element_text_view),DataModel(R.layout.grid_layout_element_text_view),
        DataModel(R.layout.grid_layout_element_text_view),DataModel(R.layout.grid_layout_element_text_view),
        DataModel(R.layout.grid_layout_element_text_view),DataModel(R.layout.grid_layout_element_text_view),
        DataModel(R.layout.grid_layout_element_text_view),DataModel(R.layout.grid_layout_element_text_view),
        DataModel(R.layout.grid_layout_element_text_view),DataModel(R.layout.grid_layout_element_text_view),
        DataModel(R.layout.grid_layout_element_text_view),DataModel(R.layout.grid_layout_element_text_view),
        DataModel(R.layout.grid_layout_element_text_view),DataModel(R.layout.grid_layout_element_text_view),
        DataModel(R.layout.grid_layout_element_text_view,clickable = true),DataModel(R.layout.grid_layout_element_text_view)
    )
    private val mutableListOfForuthColumn= mutableListOf<DataModel>(
        DataModel(R.layout.grid_layout_element_text_view,clickable = true),DataModel(R.layout.grid_layout_element_text_view,clickable = true),
        DataModel(R.layout.grid_layout_element_text_view,clickable = true),DataModel(R.layout.grid_layout_element_text_view,clickable = true),
        DataModel(R.layout.grid_layout_element_text_view,clickable = true),DataModel(R.layout.grid_layout_element_text_view,clickable = true),
        DataModel(R.layout.grid_layout_element_text_view,clickable = true),DataModel(R.layout.grid_layout_element_text_view,clickable = true),
        DataModel(R.layout.grid_layout_element_text_view,clickable = true),DataModel(R.layout.grid_layout_element_text_view,clickable = true),
        DataModel(R.layout.grid_layout_element_text_view,clickable = true),DataModel(R.layout.grid_layout_element_text_view,clickable = true),
        DataModel(R.layout.grid_layout_element_text_view,clickable = true),DataModel(R.layout.grid_layout_element_text_view,clickable = true),
        DataModel(R.layout.grid_layout_element_text_view,clickable = true),DataModel(R.layout.grid_layout_element_text_view,clickable = true)
    )
    private val mutableListOfFifthColumn= mutableListOf<DataModel>(
        DataModel(R.layout.grid_layout_element_text_view),DataModel(R.layout.grid_layout_element_text_view),
        DataModel(R.layout.grid_layout_element_text_view),DataModel(R.layout.grid_layout_element_text_view),
        DataModel(R.layout.grid_layout_element_text_view),DataModel(R.layout.grid_layout_element_text_view),
        DataModel(R.layout.grid_layout_element_text_view),DataModel(R.layout.grid_layout_element_text_view),
        DataModel(R.layout.grid_layout_element_text_view),DataModel(R.layout.grid_layout_element_text_view),
        DataModel(R.layout.grid_layout_element_text_view),DataModel(R.layout.grid_layout_element_text_view),
        DataModel(R.layout.grid_layout_element_text_view),DataModel(R.layout.grid_layout_element_text_view),
        DataModel(R.layout.grid_layout_element_text_view),DataModel(R.layout.grid_layout_element_text_view)
    )
    private val mutableListOfSixthColumn= mutableListOf<DataModel>(
        DataModel(R.layout.grid_layout_element_text_view),DataModel(R.layout.grid_layout_element_text_view),
        DataModel(R.layout.grid_layout_element_text_view),DataModel(R.layout.grid_layout_element_text_view),
        DataModel(R.layout.grid_layout_element_text_view),DataModel(R.layout.grid_layout_element_text_view),
        DataModel(R.layout.grid_layout_element_text_view),DataModel(R.layout.grid_layout_element_text_view),
        DataModel(R.layout.grid_layout_element_text_view),DataModel(R.layout.grid_layout_element_text_view),
        DataModel(R.layout.grid_layout_element_text_view),DataModel(R.layout.grid_layout_element_text_view),
        DataModel(R.layout.grid_layout_element_text_view),DataModel(R.layout.grid_layout_element_text_view),
        DataModel(R.layout.grid_layout_element_text_view),DataModel(R.layout.grid_layout_element_text_view)
    )

    private var  mutableMapOfElements = mutableMapOf<Int,MutableList<DataModel>>(
        0 to mutableListOfFirstColumn ,
        1 to  mutableListOfSecondColumns,
        2 to mutableListOfThirdColumn,
        3 to mutableListOfForuthColumn,
        4 to mutableListOfFifthColumn,
        5 to mutableListOfSixthColumn
    )

    fun getMutableMapOfElements() : MutableMap<Int, MutableList<DataModel>>{
        return mutableMapOfElements
    }

    fun setMutableMapOfElements(mutableMap : MutableMap<Int, MutableList<DataModel>>){
        this.mutableMapOfElements = mutableMap
    }

}