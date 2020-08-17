package com.example.bacanjekockica

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.autofillgridlayoutmanagerapplication.*
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.pop_up_dialog.PopUpWhenClickedDialog
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.recylcer.AutoFillGridLayoutManager
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.recylcer.RecyclerAdapterForDisplayingYambGame
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.*
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.*

import kotlinx.android.synthetic.main.fragment_yamb_layout.*



class FragmentYamb() : Fragment(), ViewModelStoreOwner , IGetDiceRolledToFragmentYamb,IGetPickedItemData, IDisplayPopUpListener{

    private lateinit var viewModel : FragmentYambViewModel
    private val popUpWhenClicked = PopUpWhenClickedDialog()
    private var adapter : RecyclerAdapterForDisplayingYambGame? = null
    var onValueInsertedListener : ICubeDataReciver? = null
    var onGameFinishedListener: IFinishedGameListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_yamb_layout,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = adapter ?: RecyclerAdapterForDisplayingYambGame(requireContext().applicationContext, viewModel.itemsInRecycler.value!!,viewModel::changeIsPopUpDialogEnabledState)

        popUpWhenClicked.onPopUpOpenListener = this
        popUpWhenClicked.onButtonYesClickedListener = this

        val layoutManager = AutoFillGridLayoutManager(requireContext().applicationContext, ScreenValues.DEVICE_WIDTH.size / ScreenValues.COLUMN_NUMBER.size)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true
        )

        viewModel.isPopUpEnabled.observe(viewLifecycleOwner, Observer {
            if(it == PopUpState.SHOW){
                popUpWhenClicked.show(requireActivity().supportFragmentManager, "PopUp")
            }
        })

        viewModel.itemsInRecycler.observe(viewLifecycleOwner, Observer {
            adapter!!.changeYambGameForDisplay(it)
        })

        viewModel.aheadCall.observe(viewLifecycleOwner, Observer {
            if(it == AheadCall.CALLED)
                viewModel.changeItemsWhenAheadCallIsCalled()
        })

        viewModel.isRecyclerEnabled.observe(viewLifecycleOwner, Observer {
            if(it == Recycler.ENABLED)
                recyclerView.suppressLayout(false)
            else
                recyclerView.suppressLayout(true)
        })

        viewModel.sendDataToPopUp.observe(viewLifecycleOwner, Observer {
            if(it == SendingDataToPopUp.ENABLED){
                Log.i("enableSending","observer")
                popUpWhenClicked.setPopUpDialogText(viewModel.onClickItemPosition,viewModel.diceRolled)
            }

        })

        viewModel.isButtonForChangingFragmentsEnabled.observe(viewLifecycleOwner, Observer {
            if(it == ButtonForChangingFragmentsState.ENABLED)
                onValueInsertedListener?.setButtonForChangingFragments(true)
        })

        viewModel.finishGameState.observe(viewLifecycleOwner, Observer {
            if(it == FinishGameState.FINISHED){
                onGameFinishedListener?.changeResultScreenState()
                onGameFinishedListener?.changeTotalPointsState(it.totalPoints)
            }

    })

    }

    override fun onDetach() {
        viewModel.unFreezeAllItems()
        viewModel.changeButtonForChangingFragmentsState()
        onValueInsertedListener!!.changeViewPastGamesButtonState(true)
        super.onDetach()
    }

    override fun getDiceRolled(diceRolled : List<Int>) {
        viewModel.diceRolled = diceRolled
    }

    override fun getAheadCall(aheadCall: Boolean) {
        viewModel = ViewModelProvider(onValueInsertedListener as ViewModelStoreOwner).get(FragmentYambViewModel::class.java)
        viewModel.changeAheadCall(aheadCall)

    }
    override fun getPickedItemData(valueOfItemPicked: Int) {
        viewModel.changeItemsWhenPopUpForEnteringValuesCloses(valueOfItemPicked)
        viewModel.changeButtonForChangingFragmentsState()
    }
    override fun enableSendingDataToPopUp() {
        viewModel.enableSendingDataToPup()
    }

    fun resetItems(){
        viewModel.changeIsGameFinishedState()
        viewModel.resetAllItems()
    }

    fun changeRecyclerEnabledState(){
        viewModel.changeIsRecyclerScrollingAndClickingEnabled()
}

    fun saveGame(){
        viewModel.saveGame(requireContext())
    }


}





