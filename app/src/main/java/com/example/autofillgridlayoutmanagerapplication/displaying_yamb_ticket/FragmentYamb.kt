package com.example.bacanjekockica

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.autofillgridlayoutmanagerapplication.*
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.pop_up_dialog.PopUpWhenClickedDialog
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.recylcer.AutoFillGridLayoutManager
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.recylcer.RecyclerAdapter
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.ScreenValues
import com.example.autofillgridlayoutmanagerapplication.changing_fragments.MainActivity
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.FragmentYambViewModel
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.PopUpState
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.IGetDiceRolledToFragmentYamb
import kotlinx.android.synthetic.main.fragment_yamb_layout.*



class FragmentYamb() : Fragment(), ViewModelStoreOwner , IGetDiceRolledToFragmentYamb {

    lateinit var viewModel : FragmentYambViewModel
    private val popUpWhenClicked = PopUpWhenClickedDialog()
    lateinit var adapter : RecyclerAdapter
    lateinit var mainActivity : MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_yamb_layout,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RecyclerAdapter(requireContext().applicationContext, viewModel.getMutableMapOfElements(), viewModel.aheadCall,
                {
                    (activity as MainActivity).setButtonForChangingFragments(true)  //postavlja button u MainActivityu da bude klikabilan nakon sto se odabere "DA" u Pop Up dialogu
                })
        { position, rowIndex ->
            viewModel.changePopUpState(position,rowIndex)
        }

        val layoutManager = AutoFillGridLayoutManager(requireContext().applicationContext, ScreenValues.DEVICE_WIDTH.size / ScreenValues.COLUMN_NUMBER.size)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true
        )

        popUpWhenClicked.adapter = this.adapter
        popUpWhenClicked.fragment = this

        viewModel.isPopUpEnabled.observe(viewLifecycleOwner, Observer {
            if(it == PopUpState.SHOW){
                popUpWhenClicked.show(requireActivity().supportFragmentManager, "PopUp")
                popUpWhenClicked.setPopUpDialogText(itemClicked = it.position, row = it.rowIndex, diceRolled = viewModel.diceRolled)
            }
        })

        }

    override fun onDetach() {
        super.onDetach()
        viewModel.updateLastItemClicked()
    }

    override fun getDiceRolled(diceRolled : List<Int>) {
        viewModel.diceRolled = diceRolled
    }

    override fun getAheadCall(aheadCall: Boolean) {
        viewModel = ViewModelProvider(mainActivity).get(FragmentYambViewModel::class.java)
        viewModel.aheadCall = aheadCall
    }


}





