package com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.FragmentYamb

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.autofillgridlayoutmanagerapplication.*
import com.example.autofillgridlayoutmanagerapplication.database.GamesPlayedDatabase
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.pop_up_dialog_for_inserting_values.PopUpItemClicked
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.recyclerAdapter.AutoFillGridLayoutManager
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.recyclerAdapter.RecyclerAdapterForDisplayingYambGame
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.pop_up_dialog_for_finished_game.PopUpFinishedGame
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.*

import kotlinx.android.synthetic.main.fragment_yamb_layout.*



class FragmentYambTicket() : Fragment(),IGetPickedItemData, IDisplayPopUpListener, ISaveOrContinueListener,ISendTotalPoints{

    private lateinit var viewModel : ViewModelFragmentYambTicket
    private lateinit var popUpWhenClicked : PopUpItemClicked
    private lateinit var popUpFinishedGame : PopUpFinishedGame
    private var adapter : RecyclerAdapterForDisplayingYambGame? = null



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_yamb_layout,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(GamesPlayedDatabase.getInstanceOfDatabase(requireContext()))
        ).get(ViewModelFragmentYambTicket::class.java)

        setupPopUpDialogsAndLayoutManager()
        viewModel.setupObserverForDataAboutGame()

        viewModel.isPopUpForEnteringValuesEnabled.observe(viewLifecycleOwner, Observer {
            if(it)
                popUpWhenClicked.show(requireActivity().supportFragmentManager, "PopUp")
        })
        viewModel.isSendingPositionOfItemClickedToPopUpEnabled.observe(viewLifecycleOwner, Observer {
            if(it)
                viewModel.changePositionOfLastItemClicked()
        })
        viewModel.onClickItemPosition.observe(viewLifecycleOwner, Observer {
            popUpWhenClicked.setPopUpDialogTextForGivenPosition(it)
            viewModel.changeSendingPositionOfItemClickedToPopUpForEnteringValuesState()
        })
        viewModel.itemsInRecycler.observe(viewLifecycleOwner, Observer {
            if(adapter == null) {
                adapter = RecyclerAdapterForDisplayingYambGame(requireContext().applicationContext, listOf())
                recyclerView.adapter = adapter
                adapter!!.viewModelReference = viewModel
            }
            adapter!!.changeYambGameForDisplay(it)
        })
        viewModel.aheadCall.observe(viewLifecycleOwner, Observer {
            if(it)
                viewModel.changeItemsWhenAheadCallIsCalled()
        })
        viewModel.areDicesRolledTwice.observe(viewLifecycleOwner, Observer {
            if(it)
                viewModel.unFreezeAllItems()
        })
        viewModel.finishGameState.observe(viewLifecycleOwner, Observer {
            if(it)
                popUpFinishedGame.show(requireActivity().supportFragmentManager,"FINISHED GAME")
        })
        viewModel.isSendingTotalPointsToFinishedGamePopUpEnabled.observe(viewLifecycleOwner, Observer {
            if(it)
                viewModel.changeTotalPoints()
        })
        viewModel.totalPoints.observe(viewLifecycleOwner, Observer {
            popUpFinishedGame.getTotalPoints(it)
            viewModel.changeSendingTotalPointsToFinishedGamePopUpState()
        })
        viewModel.showToastForGameSaved.observe(viewLifecycleOwner, Observer {
            if(it)
                Toast.makeText(requireContext(),"Game saved.",Toast.LENGTH_SHORT).show()
        })

    }

    override fun getPickedItemDataFromPopUpForInsertingValues(valueOfItemPicked: Int) {
        viewModel.changeItemsWhenPopUpForEnteringValuesCloses(valueOfItemPicked)
    }
    override fun enableSendingDataToPopUpForInsertingValues() {
        viewModel.changeSendingPositionOfItemClickedToPopUpForEnteringValuesState()
    }
    override fun saveGame(){
        viewModel.saveGame()
    }
    override fun resetItems(){
        viewModel.resetAllItems()
    }
    override fun sendTotalPoints(){
        viewModel.changeSendingTotalPointsToFinishedGamePopUpState()
    }

    private fun setupPopUpDialogsAndLayoutManager(){

        val layoutManager = AutoFillGridLayoutManager(requireContext().applicationContext, ScreenValues.DEVICE_WIDTH.size / ScreenValues.COLUMN_NUMBER.size)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        popUpWhenClicked = PopUpItemClicked()
        popUpWhenClicked.database = GamesPlayedDatabase.getInstanceOfDatabase(requireContext())
        popUpWhenClicked.onPopUpOpenListener = this
        popUpWhenClicked.onButtonYesClickedListener = this

        popUpFinishedGame = PopUpFinishedGame()
        popUpFinishedGame.onSaveOrContinueClickedListener = this
        popUpFinishedGame.isCancelable = false
        popUpFinishedGame.onViewModelInitializedListener = this

    }
    override fun onDestroyView() {
        viewModel.disposeOfObservers()
        super.onDestroyView()
    }



}





