package com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.pop_up_dialog


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.autofillgridlayoutmanagerapplication.R
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.recylcer.RecyclerAdapter
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.ISetLastItemClickedInPopUpDialog
import com.example.bacanjekockica.FragmentYamb
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.pop_up_when_clicked_dialog_layout.*

class PopUpWhenClickedDialog() : BottomSheetDialogFragment(), ISetLastItemClickedInPopUpDialog {

    lateinit var viewModel : PopUpWhenClickedDialogViewModel
    lateinit var adapter : RecyclerAdapter
    lateinit var fragment : FragmentYamb

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.pop_up_when_clicked_dialog_layout,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.text.observe(this,Observer<String>{
            tvDialog.text = it
        })

        viewModel.pictureSources.observe(this,Observer<List<Int>>{
            setCubePicturesSource(it)
        })


        btnDialogYes.setOnClickListener {
            adapter.updateRecyclerState(viewModel.positionOfItemClickedInRecycler,viewModel.getValueForInput())
            this.dismiss()
        }
        btnDialogNo.setOnClickListener {
            this.dismiss()
        }

    }

    private fun setCubePicturesSource(listOfPicureSources : List<Int>) {
        ivSheetDialogDiceRolledOne.setImageResource(listOfPicureSources[0])
        ivSheetDialogDiceRolledTwo.setImageResource(listOfPicureSources[1])
        ivSheetDialogDiceRolledThree.setImageResource(listOfPicureSources[2])
        ivSheetDialogDiceRolledFour.setImageResource(listOfPicureSources[3])
        ivSheetDialogDiceRolledFive.setImageResource(listOfPicureSources[4])
        ivSheetDialogDiceRolledSix.setImageResource(listOfPicureSources[5])
    }


    override fun setPopUpDialogText(itemClicked: Int, row: Int,diceRolled : MutableList<Int>) {
        viewModel = ViewModelProvider(fragment).get(PopUpWhenClickedDialogViewModel::class.java)
        viewModel.setDiceRolled(diceRolled)
        viewModel.setTextForDisplay(itemClicked,row)
    }



}
