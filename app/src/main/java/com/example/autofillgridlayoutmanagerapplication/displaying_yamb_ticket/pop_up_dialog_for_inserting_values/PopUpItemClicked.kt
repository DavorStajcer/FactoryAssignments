package com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.pop_up_dialog_for_inserting_values


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.autofillgridlayoutmanagerapplication.database.GamesPlayedDatabase
import com.example.autofillgridlayoutmanagerapplication.databinding.PopUpWhenClickedDialogBinding
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.IDisplayPopUpListener
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.IGetPickedItemData
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.ISetLastItemClickedInPopUpDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.pop_up_when_clicked_dialog.*

class PopUpItemClicked() : BottomSheetDialogFragment(), ISetLastItemClickedInPopUpDialog {

    private var viewModel : PopUpWhenClickedDialogViewModel? = null
    var onPopUpOpenListener : IDisplayPopUpListener? = null
    var onButtonYesClickedListener : IGetPickedItemData? = null
    var database : GamesPlayedDatabase? = null
    lateinit var popUpBindingLayout : PopUpWhenClickedDialogBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        popUpBindingLayout = PopUpWhenClickedDialogBinding.inflate(inflater,container,false)
        return popUpBindingLayout.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = viewModel ?: ViewModelProvider(this).get(PopUpWhenClickedDialogViewModel::class.java)
        viewModel!!.setDiceRolleDatabaseObserver(database!!)
        viewModel!!.dataForBinding.observe(this, Observer {
            popUpBindingLayout.data = it
        })
        viewModel!!.sendingDataOfItemChosen.observe(this, Observer {
            if(it == SendDataOfItemChosen.ENABLED)
                onButtonYesClickedListener!!.getPickedItemDataFromPopUpForInsertingValues(it.valueForInput)
        })
        viewModel!!.recivePositionOfPickedItem.observe(this, Observer {
            if(it)
                onPopUpOpenListener!!.enableSendingDataToPopUpForInsertingValues()
        })
        btnDialogYes.setOnClickListener {
            viewModel?.sendDataOfItemPickedBack()
            this.dismiss()
        }
        btnDialogNo.setOnClickListener {
            this.dismiss()
        }

    }

    override fun setPopUpDialogTextForGivenPosition(positionOfItemClicked: Int) {
        viewModel!!.setTextForDisplay(positionOfItemClicked)
    }

    override fun onDetach() {
        viewModel!!.changeShouldPopUpRecivePoistionOfPickedItem()
        super.onDetach()
    }



}
