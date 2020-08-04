package com.example.bacanjekockica

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.autofillgridlayoutmanagerapplication.*
import kotlinx.android.synthetic.main.fragment_yamb_ticket.*







class FragmentYamb() : Fragment(){

    lateinit var viewModel: FragmentYambViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        Log.i("TAG","onCREATEVIEW")
        return inflater.inflate(R.layout.fragment_yamb_ticket,container,false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        this.viewModel = ViewModelProvider(this).get(FragmentYambViewModel::class.java)
/*
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
        recyclerView.setHasFixedSize(true)*/
    }

/*    override fun updateDiceRolled(diceRolled: MutableList<Int>,aheadCallInYamb: Boolean) {
        Log.i("DICEROLLED","$diceRolled")
        Log.i("DICEROLLED","Najava -> $aheadCallInYamb")            //stavi dice rolled od ovog fragmenta na pravu vrijednost i ahead Call
        this.diceRolled = diceRolled
        this.aheadCallInYamb = aheadCallInYamb
    }

    override fun onDetach() {
        super.onDetach()
        this.adapter?.updateLastItemClicked(this.positionOfLastItemClickedInRecycler) //update-aj klikabilnost elemenata u recylceru
    }*/


}



