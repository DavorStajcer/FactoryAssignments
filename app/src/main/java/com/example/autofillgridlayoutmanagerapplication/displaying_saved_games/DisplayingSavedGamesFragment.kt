package com.example.autofillgridlayoutmanagerapplication.displaying_saved_games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.autofillgridlayoutmanagerapplication.R
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.filling_yamb_ticket.recyclerAdapter.AutoFillGridLayoutManager
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.filling_yamb_ticket.recyclerAdapter.DisplayingYambGameRecyclerAdapter
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.Adapters
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.IOnGameClickedListener
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.ScreenValues
import com.example.autofillgridlayoutmanagerapplication.view_model_factories.SavedGamesViewModelFactory
import kotlinx.android.synthetic.main.saved_games_fragment.*



class DisplayingSavedGamesFragment  : Fragment() , IOnGameClickedListener {

    private lateinit var viewModelSavedGames : DisplayingSavedGamesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.saved_games_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelSavedGames = ViewModelProvider(
            this, SavedGamesViewModelFactory(requireContext())
        ).get(DisplayingSavedGamesViewModel::class.java)

        val adapterGameStats = DisplayingAllSavedGamesRecyclerAdapter(requireContext(),listOf())
        val adapterYambTicketToDisplay = DisplayingYambGameRecyclerAdapter(requireContext(), listOf())
        val autoFillGridLayoutManager = AutoFillGridLayoutManager(requireContext().applicationContext, ScreenValues.DEVICE_WIDTH.size / ScreenValues.COLUMN_NUMBER.size)
        adapterGameStats.onClickListener = this
        adapterYambTicketToDisplay.viewModelReference = viewModelSavedGames
        recyclerPastGamesList.layoutManager = autoFillGridLayoutManager

        initializeLiveDataObservers(adapterGameStats,adapterYambTicketToDisplay,autoFillGridLayoutManager)
    }
    private fun initializeLiveDataObservers(adapterGameStats : DisplayingAllSavedGamesRecyclerAdapter, adapterYambTicketToDisplay : DisplayingYambGameRecyclerAdapter, autoFillGridLayoutManager: AutoFillGridLayoutManager){
        viewModelSavedGames.listOfPlayedGames.observe(viewLifecycleOwner, Observer {
            adapterGameStats.changeListOfGameStatsForDisplay(it)
        })
        viewModelSavedGames.listOfItemsInChosenGame.observe(viewLifecycleOwner, Observer {
            adapterYambTicketToDisplay.changeYambGameForDisplay(it)
        })
        viewModelSavedGames.textAbouveDisplayedGame.observe(viewLifecycleOwner, Observer {

            tvPastGamesTitle.text = it
        })
        viewModelSavedGames.floatingActionButton.observe(viewLifecycleOwner,Observer{
            if(it){
                floatingActionButton_BackToListOfGames.visibility = View.VISIBLE
                setClickListenerForFloatingButton()
            }
            else{
                floatingActionButton_BackToListOfGames.visibility = View.INVISIBLE
                removeClickListenerForFloatingButton()
            }

        })
        viewModelSavedGames.currentAdapter.observe(viewLifecycleOwner, Observer {
            when(it!!){
                Adapters.GAME_STATS ->{
                    changeAdapter(adapterGameStats)
                    changeColumntWidthOfLayoutManager(ScreenValues.DEVICE_WIDTH.size,autoFillGridLayoutManager)
                    viewModelSavedGames.changeFloatingActionButtonState()
                    viewModelSavedGames.changeTextAboveRecycler()
                }
                Adapters.YAMB_GAME -> {
                    changeAdapter(adapterYambTicketToDisplay)
                    changeColumntWidthOfLayoutManager(ScreenValues.DEVICE_WIDTH.size / ScreenValues.COLUMN_NUMBER.size,autoFillGridLayoutManager)
                    viewModelSavedGames.changeFloatingActionButtonState()
                    viewModelSavedGames.changeTextAboveRecycler()
                }
            }

        })
        viewModelSavedGames.isDatabaseEmpty.observe(viewLifecycleOwner, Observer {
            showEmptyDatabaseNotification(it)
        })
    }
    private fun changeAdapter(adapter : RecyclerView.Adapter<RecyclerView.ViewHolder>){
        recyclerPastGamesList.adapter = adapter
    }
    private fun changeColumntWidthOfLayoutManager(widht : Int, autoFillGridLayoutManager: AutoFillGridLayoutManager){
        autoFillGridLayoutManager.columnWidth = widht
        autoFillGridLayoutManager.columnWidthChanged = true
    }
    private fun setClickListenerForFloatingButton(){
        floatingActionButton_BackToListOfGames.setOnClickListener {
            viewModelSavedGames.changeAdapter()
        }

    }
    private fun removeClickListenerForFloatingButton() {
       floatingActionButton_BackToListOfGames.setOnContextClickListener(null)
   }
    private fun showEmptyDatabaseNotification(isDatabaseEmpty : Boolean){
        if(isDatabaseEmpty)
            tvEmptyDatabase.visibility = View.VISIBLE
        else
            tvEmptyDatabase.visibility = View.INVISIBLE
    }

    override fun onDestroyView() {
        viewModelSavedGames.disposeOfObservers()
        super.onDestroyView()
    }
    override fun showClickedGame(gameId: Long) {
        viewModelSavedGames.showClickedGame(gameId)
    }


}