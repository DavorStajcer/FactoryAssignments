package com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.filling_yamb_ticket

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
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.displaying_pop_up_dialog_when_item_clicked.DisplayingPopUpDialogForItemClickedInGame
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.recyclerAdapter.AutoFillGridLayoutManager
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.recyclerAdapter.DisplayingYambGameRecyclerAdapter
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.displaying_pop_up_dialog_when_game_finished.DisplayingFinishedGamePopUpDialog
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.*
import com.example.autofillgridlayoutmanagerapplication.view_model_factory.ViewModelFactory

import kotlinx.android.synthetic.main.filling_yamb_ticket_fragment.*



class FillingYambTicketFragment() : Fragment(),IGetPickedItemData, IDisplayPopUpListener, ISaveOrContinueListener,ISendTotalPoints{

    private lateinit var viewModel : FillingYambTicketViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.filling_yamb_ticket_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, ViewModelFactory(GamesPlayedDatabase.getInstanceOfDatabase(requireContext()))).get(FillingYambTicketViewModel::class.java)
        val layoutManager = AutoFillGridLayoutManager(requireContext().applicationContext, ScreenValues.DEVICE_WIDTH.size / ScreenValues.COLUMN_NUMBER.size)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        val popUpWhenClicked = DisplayingPopUpDialogForItemClickedInGame()
        val popUpFinishedGame = DisplayingFinishedGamePopUpDialog()
        val adapter =  DisplayingYambGameRecyclerAdapter(requireContext().applicationContext, listOf())
        recyclerView.adapter = adapter
        adapter.viewModelReference = viewModel
        popUpWhenClicked.database = GamesPlayedDatabase.getInstanceOfDatabase(requireContext())
        popUpWhenClicked.onPopUpOpenListener = this
        popUpWhenClicked.onButtonYesClickedListener = this
        popUpFinishedGame.onSaveOrContinueClickedListener = this
        popUpFinishedGame.isCancelable = false
        popUpFinishedGame.onViewModelInitializedListener = this

        viewModel.isPopUpForEnteringValuesEnabled.observe(viewLifecycleOwner, Observer {
            if(it)
                popUpWhenClicked.show(requireActivity().supportFragmentManager, "PopUp")
        })
        viewModel.onClickItemPosition.observe(viewLifecycleOwner, Observer {
            popUpWhenClicked.setPopUpDialogTextForGivenPosition(it)
        })
        viewModel.itemsInRecycler.observe(viewLifecycleOwner, Observer {
            adapter.changeYambGameForDisplay(it)
        })
        viewModel.areDicesRolledTwice.observe(viewLifecycleOwner, Observer {
            if(it)
                viewModel.unFreezeAllItems()
        })
        viewModel.finishGameState.observe(viewLifecycleOwner, Observer {
            if(it)
                popUpFinishedGame.show(requireActivity().supportFragmentManager,"FINISHED GAME")
        })
        viewModel.totalPoints.observe(viewLifecycleOwner, Observer {
            popUpFinishedGame.getTotalPoints(it)
        })
        viewModel.showToastForGameSaved.observe(viewLifecycleOwner, Observer {
            if(it)
                Toast.makeText(requireContext(),"Game saved.",Toast.LENGTH_SHORT).show()
        })

    }

    override fun getValueInsertedInClickedItem(valueOfItemPicked: Int) {
        viewModel.changeListOfItemsWhenValueInserted(valueOfItemPicked)
    }
    override fun sendPositionOfItemClickedToPopUpDialog() {
        viewModel.changePositionOfLastItemClicked()
    }
    override fun saveGame(){
        viewModel.saveGame()
    }
    override fun resetItems(){
        viewModel.resetAllItems()
    }
    override fun sendTotalPoints(){
        viewModel.changeTotalPoints()
    }
    override fun onDestroyView() {
        viewModel.disposeOfObservers()
        super.onDestroyView()
    }



}





