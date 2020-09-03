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
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.filling_yamb_ticket.recyclerAdapter.AutoFillGridLayoutManager
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.filling_yamb_ticket.recyclerAdapter.DisplayingYambGameRecyclerAdapter
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.displaying_pop_up_dialog_when_game_finished.DisplayingFinishedGamePopUpDialog
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.*
import com.example.autofillgridlayoutmanagerapplication.view_model_factories.ViewModelFactory
import kotlinx.android.synthetic.main.filling_yamb_ticket_fragment.*



class FillingYambTicketFragment() : Fragment(),IGetPickedItemData, ISaveOrContinueListener{

    private lateinit var viewModel : FillingYambTicketViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.filling_yamb_ticket_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, ViewModelFactory(GamesPlayedDatabase.getInstanceOfDatabase(requireContext()))).get(FillingYambTicketViewModel::class.java)
        val layoutManager = AutoFillGridLayoutManager(requireContext().applicationContext, ScreenValues.DEVICE_WIDTH.size / ScreenValues.COLUMN_NUMBER.size)
        val itemClickedPopUp = DisplayingPopUpDialogForItemClickedInGame()
        val finishedGamePopUp = DisplayingFinishedGamePopUpDialog()
        val adapter =  DisplayingYambGameRecyclerAdapter(requireContext().applicationContext, listOf())
        adapter.viewModelReference = viewModel

        setUpRecyclerView(layoutManager,adapter)
        setUpPopItemClickedPopUp(itemClickedPopUp)
        setUpFinishedGamePopUp(finishedGamePopUp)
        viewModel.setupObserverForDataAboutGame()

        viewModel.isPopUpForEnteringValuesEnabled.observe(viewLifecycleOwner, Observer {
            if(it)
                itemClickedPopUp.show(requireActivity().supportFragmentManager, getString(R.string.entering_values_pop_up_tag))
        })
        viewModel.itemsInRecycler.observe(viewLifecycleOwner, Observer {
            adapter.changeYambGameForDisplay(it)
        })
        viewModel.isPopUpForFinishedGameEnabled.observe(viewLifecycleOwner, Observer {
            if(it)
                finishedGamePopUp.show(requireActivity().supportFragmentManager,getString(R.string.finished_game_pop_up_tag))
        })
        viewModel.showToastForGameSaved.observe(viewLifecycleOwner, Observer {
            if(it)
                Toast.makeText(requireContext(),getString(R.string.saved_game_toast_message),Toast.LENGTH_SHORT).show()
        })

    }

    private fun setUpRecyclerView(layoutManager: AutoFillGridLayoutManager, adapter: DisplayingYambGameRecyclerAdapter) {
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
    }
    private fun setUpPopItemClickedPopUp(itemClickedPopUp : DisplayingPopUpDialogForItemClickedInGame){
        itemClickedPopUp.database = GamesPlayedDatabase.getInstanceOfDatabase(requireContext())
        itemClickedPopUp.onButtonYesClickedListener = this
    }
    private fun setUpFinishedGamePopUp(finishedGamePopUp : DisplayingFinishedGamePopUpDialog){
        finishedGamePopUp.onSaveOrContinueClickedListener = this
        finishedGamePopUp.isCancelable = false
    }

    override fun getValueInsertedInClickedItem(valueOfItemPicked: Int) {
        viewModel.makeChangesAfterValueIsInserted(valueOfItemPicked)
    }
    override fun resetItemsAndSaveGame(){
        viewModel.saveGameStatsAndPlayedColumnsAndDisplayStartingItems()
    }
    override fun resetItems(){
        viewModel.displayStartingItems()
    }
    override fun onDestroyView() {
        viewModel.disposeOfObservers()
        super.onDestroyView()
    }



}




