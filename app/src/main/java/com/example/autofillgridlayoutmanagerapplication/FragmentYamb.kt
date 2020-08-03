package com.example.bacanjekockica

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.autofillgridlayoutmanagerapplication.*
import kotlinx.android.synthetic.main.fragment_yamb_ticket.*







class FragmentYamb() : Fragment(), ICubesRolledObserver{

    var bottomSheetListener : ISetDiceRolledOnBottomSheetObserver? = null
    var adapter : IUpdateLastItemClickedInRecycler? = null
    var positionOfLastItemClickedInRecycler = 0

    var diceRolled = mutableListOf<Int>()
    var aheadCallInYamb : Boolean = false

    var fragmentManagerListic : FragmentManager? = null


    private var mutableListOfFirstColumn = mutableListOf<DataModel>(
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



     var mutableMapOfElements = mutableMapOf<Int,MutableList<DataModel>>(
        0 to mutableListOfFirstColumn ,
        1 to  mutableListOfSecondColumns,
        2 to mutableListOfThirdColumn,
        3 to mutableListOfForuthColumn,
        4 to mutableListOfFifthColumn,
        5 to mutableListOfSixthColumn
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        Log.i("TAG","onCREATEVIEW")
        return inflater.inflate(R.layout.fragment_yamb_ticket,container,false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomSheetDialog = BottomSheetDialog()
        bottomSheetListener = bottomSheetDialog
        bottomSheetListener?.updateDiceRolledBottomSheet(diceRolled)

        val adapter = RecyclerAdapter(context!!.applicationContext, mutableMapOfElements,aheadCallInYamb,itemClick ={ position, rowIndex ->
            bottomSheetDialog.positionOfItemChanged = position
            bottomSheetDialog.rowIndex = rowIndex                                   //sluzi za proslijedjivanje trenutno stisnute pozicije (ne mora biti unesen podatak na toj poziciji)
            bottomSheetDialog.show(fragmentManagerListic!!,"Bottom sheet")
        })
        {positionOfLastItemClicked ->
                this.positionOfLastItemClickedInRecycler = positionOfLastItemClicked //sluzi da proslijedim poziciju zadnjeg elementa
        }

        recyclerView.adapter = adapter
        this.adapter = adapter
        adapter.aheadCallInYamb = aheadCallInYamb
        bottomSheetDialog.recyclerAdapterStateListener = adapter
        val layoutManager = AutoFillGridLayoutManager(context!!.applicationContext, ScreenValues.DEVICE_WIDTH.size/ScreenValues.COLUMN_NUMBER.size)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
    }

    override fun updateDiceRolled(diceRolled: MutableList<Int>,aheadCallInYamb: Boolean) {
        Log.i("DICEROLLED","$diceRolled")
        Log.i("DICEROLLED","Najava -> $aheadCallInYamb")            //stavi dice rolled od ovog fragmenta na pravu vrijednost i ahead Call
        this.diceRolled = diceRolled
        this.aheadCallInYamb = aheadCallInYamb
    }

    override fun onDetach() {
        super.onDetach()
        this.adapter?.updateLastItemClicked(this.positionOfLastItemClickedInRecycler) //update-aj klikabilnost elemenata u recylceru
    }


}



