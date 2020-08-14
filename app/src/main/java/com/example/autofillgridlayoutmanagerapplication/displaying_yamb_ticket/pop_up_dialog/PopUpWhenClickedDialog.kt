package com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.pop_up_dialog


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.autofillgridlayoutmanagerapplication.R
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.IDisplayPopUpListener
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.IGetPickedItemData
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.ISetLastItemClickedInPopUpDialog
import com.example.bacanjekockica.FragmentYamb
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.pop_up_when_clicked_dialog_layout.*

class PopUpWhenClickedDialog() : BottomSheetDialogFragment(), ISetLastItemClickedInPopUpDialog {

    private var viewModel : PopUpWhenClickedDialogViewModel? = null
    var onPopUpOpenListener : IDisplayPopUpListener? = null
    var onButtonYesClickedListener : IGetPickedItemData? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.pop_up_when_clicked_dialog_layout,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(PopUpWhenClickedDialogViewModel::class.java)

        onPopUpOpenListener!!.enableSendingDataToPopUp() //daje do znanja da se view od fragmenta kreirao i da se podaci mogu proslijediti

        viewModel?.text?.observe(this,Observer<String>{
            tvDialog.text = it
        })

        viewModel?.pictureSources?.observe(this,Observer<List<Int>>{
            setCubePicturesSource(it)
        })

        viewModel?.sendingDataOfItemChosen?.observe(this, Observer {
            if(it == SendDataOfItemChosen.ENABLED)
                onButtonYesClickedListener!!.getPickedItemData(it.valueForInput)
        })

        btnDialogYes.setOnClickListener {
            viewModel?.sendDataOfItemPickedBack()
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


    override fun setPopUpDialogText(positionOfItemClicked: Int,diceRolled : List<Int>) {
        viewModel?.setDiceRolled(diceRolled)
        viewModel?.setTextForDisplay(positionOfItemClicked)
    }



}
