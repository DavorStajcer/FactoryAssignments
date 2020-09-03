package com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.displaying_pop_up_dialog_when_item_clicked


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.autofillgridlayoutmanagerapplication.database.GamesPlayedDatabase
import com.example.autofillgridlayoutmanagerapplication.databinding.DisplayingPopUpWhenItemClickedBinding
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.IDisplayPopUpListener
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.IGetPickedItemData
import com.example.autofillgridlayoutmanagerapplication.view_model_factories.ViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.displaying_pop_up_when_item_clicked.*

class DisplayingPopUpDialogForItemClickedInGame() : BottomSheetDialogFragment(){

    private var viewModel : DisplayingPopUpForItemClickedViewModel? = null
    var onPopUpOpenListener : IDisplayPopUpListener? = null
    var onButtonYesClickedListener : IGetPickedItemData? = null
    var database : GamesPlayedDatabase? = null
    lateinit var popUpBindingLayout : DisplayingPopUpWhenItemClickedBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        popUpBindingLayout = DisplayingPopUpWhenItemClickedBinding.inflate(inflater,container,false)
        Log.i("recive","onCreateView")

        return popUpBindingLayout.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = viewModel ?: ViewModelProvider(this, ViewModelFactory(database!!)).get(DisplayingPopUpForItemClickedViewModel::class.java)

        viewModel!!.dataForBinding.observe(this, Observer {
            popUpBindingLayout.data = it
        })
        viewModel!!.sendingInsertedValueOfChosenItem.observe(this, Observer {
            if(it == SendDataOfItemChosen.ENABLED)
                onButtonYesClickedListener!!.getValueInsertedInClickedItem(it.valueForInput)
        })


        btnDialogYes.setOnClickListener {
            viewModel?.sendDataOfItemPickedBack()
            this.dismiss()
        }
        btnDialogNo.setOnClickListener {
            this.dismiss()
        }

    }
}
