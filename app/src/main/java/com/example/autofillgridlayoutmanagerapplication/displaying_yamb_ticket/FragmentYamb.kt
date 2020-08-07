package com.example.bacanjekockica

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelStoreOwner
import com.example.autofillgridlayoutmanagerapplication.*
import com.example.autofillgridlayoutmanagerapplication.changing_fragment_data.SharedViewModel
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.pop_up_dialog.PopUpWhenClickedDialog
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.recylcer.AutoFillGridLayoutManager
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.recylcer.RecyclerAdapter
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.ScreenValues
import com.example.autofillgridlayoutmanagerapplication.changing_fragments.MainActivity
import kotlinx.android.synthetic.main.fragment_yamb_layout.*


class FragmentYamb() : Fragment(), ViewModelStoreOwner {

    private val viewModel: SharedViewModel by activityViewModels()
    private val popUpWhenClicked =
        PopUpWhenClickedDialog()
    lateinit var adapter : RecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_yamb_layout,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RecyclerAdapter(requireContext().applicationContext, viewModel.getMutableMapOfElements(), viewModel.aheadCall,
                {
                    (activity as MainActivity).setButtonForChangingFragments(true)  //postavlja button u MainActivityu da bude klikabilan
                }
        )
        {
                position, rowIndex ->
                popUpWhenClicked.fragment = this
                popUpWhenClicked.show(requireActivity().supportFragmentManager, "PopUp")
                popUpWhenClicked.setPopUpDialogText(
                    itemClicked = position,
                    row = rowIndex,
                    diceRolled = viewModel.diceRolled
                )
            }

        val layoutManager = AutoFillGridLayoutManager(requireContext().applicationContext, ScreenValues.DEVICE_WIDTH.size / ScreenValues.COLUMN_NUMBER.size)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true
        )

        popUpWhenClicked.adapter = this.adapter

        }

    override fun onDetach() {
        super.onDetach()
        viewModel.updateLastItemClicked()
    }
}





