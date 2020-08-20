package com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.FragmentYamb

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.autofillgridlayoutmanagerapplication.*
import com.example.autofillgridlayoutmanagerapplication.database.GamesPlayedDatabase
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.pop_up_dialog_for_inserting_values.PopUpItemClicked
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.recylcerAdapterForDisplayingYambGame.AutoFillGridLayoutManager
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.recylcerAdapterForDisplayingYambGame.RecyclerAdapterForDisplayingYambGame
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.pop_up_dialog_for_finished_game.PopUpFinishedGame
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.*

import kotlinx.android.synthetic.main.fragment_yamb_layout.*

enum class RowIndexOfResultElements(val index : Int){
    INDEX_OF_RESULT_ROW_ELEMENT_ONE(6),INDEX_OF_RESULT_ROW_ELEMENT_TWO(9),INDEX_OF_RESULT_ROW_ELEMENT_THREE(15)
}

class Fragment_YambTicket() : Fragment(),IGetPickedItemData, IDisplayPopUpListener, ISaveOrContinueListener,IOnItemInRecyclerClickedListener,ISendTotalPoints{

    private var viewModel : ViewModel_FragmentYambTicket? = null
    private val popUpWhenClicked = PopUpItemClicked()
    private val popUpFinishedGame = PopUpFinishedGame()
    private var adapter : RecyclerAdapterForDisplayingYambGame? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_yamb_layout,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = viewModel ?: ViewModelProvider(requireActivity()).get(ViewModel_FragmentYambTicket::class.java)
        adapter = adapter ?: RecyclerAdapterForDisplayingYambGame(requireContext().applicationContext, viewModel!!.itemsInRecycler.value!!)

        setListenersInAdapterAndFragments()

        val layoutManager = AutoFillGridLayoutManager(requireContext().applicationContext, ScreenValues.DEVICE_WIDTH.size / ScreenValues.COLUMN_NUMBER.size)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true
        )

        viewModel!!.setupObserverForDataAboutGame(requireContext())

        viewModel!!.isPopUpForEnteringValuesEnabled.observe(viewLifecycleOwner, Observer {
            if(it == PopUpState.SHOW){
                popUpWhenClicked.show(requireActivity().supportFragmentManager, "PopUp")
            }
        })
        viewModel!!.itemsInRecycler.observe(viewLifecycleOwner, Observer {
            adapter!!.changeYambGameForDisplay(it)
        })
        viewModel!!.aheadCall.observe(viewLifecycleOwner, Observer {
            if(it)
                viewModel!!.changeItemsWhenAheadCallIsCalled()
        })
        viewModel!!.areDicesRolledTwice.observe(viewLifecycleOwner, Observer {
            if(it)
                viewModel!!.unFreezeAllItems()
        })
        viewModel!!.sendDataToPopUpForInsertingValues.observe(viewLifecycleOwner, Observer {
            if(it == SendingDataToPopUp.ENABLED)
                popUpWhenClicked.setPopUpDialogText(viewModel!!.onClickItemPosition)
        })
        viewModel!!.sendDataToPopUpForFinishedGame.observe(viewLifecycleOwner, Observer {
            if(it == SendingDataToFinishedGamePopUp.ENABLED)
                popUpFinishedGame.getTotalPoints(it.totalPoints)
        })
        viewModel!!.finishGameState.observe(viewLifecycleOwner, Observer {
            if(it == FinishGameState.FINISHED)
                popUpFinishedGame.show(requireActivity().supportFragmentManager,"FINISHED GAME")

    })

    }

    override fun getPickedItemDataFromPopUpForInsertingValues(valueOfItemPicked: Int) {
        viewModel!!.changeItemsWhenPopUpForEnteringValuesCloses(valueOfItemPicked)
    }
    override fun enableSendingDataToPopUpForInsertingValues() {
        viewModel!!.enableSendingDataToPopUpForInsertingValues()
    }
    override fun saveGame(){
        viewModel!!.saveGame()
    }
    override fun resetItems(){
        viewModel!!.resetAllItems()
    }
    override fun activatePopUpWhenItemClicked(positionOfItemClicked: Int) {
        viewModel!!.changeIsPopUpDialogEnabledState(positionOfItemClicked)
    }
    override fun sendTotalPoints(){
        viewModel!!.enableSendingTotalPointsToFinishedGamePopUp()
    }


    private fun setListenersInAdapterAndFragments(){
        adapter!!.onClickListener = this
        popUpWhenClicked.database = GamesPlayedDatabase.getInstanceOfDatabase(requireContext())
        popUpWhenClicked.onPopUpOpenListener = this
        popUpWhenClicked.onButtonYesClickedListener = this
        popUpFinishedGame.onSaveOrContinueClickedListener = this
        popUpFinishedGame.isCancelable = false
        popUpFinishedGame.onViewModelInitializedListener = this
    }


}





