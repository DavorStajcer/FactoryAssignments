package com.example.autofillgridlayoutmanagerapplication.savedGames

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.autofillgridlayoutmanagerapplication.R
import com.example.autofillgridlayoutmanagerapplication.database.GamesPlayedDatabase
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.FragmentYamb.ViewModelFactory
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.recyclerAdapter.AutoFillGridLayoutManager
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.recyclerAdapter.RecyclerAdapterForDisplayingYambGame
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.Adapters
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.FloatingActionButtton
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.IOnGameClickedListener
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.ScreenValues
import kotlinx.android.synthetic.main.fragment_saved_games.*



class FragmentSavedGames  : Fragment() , IOnGameClickedListener {

    private lateinit var viewModelSavedGames : ViewModelSavedGames
    private lateinit var adapterGameStats : RecyclerAdapterDisplayingSavedGames
    private lateinit var adapterYambTicketToDisplay : RecyclerAdapterForDisplayingYambGame
    private lateinit var autoFillGridLayoutManager :AutoFillGridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_saved_games,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelSavedGames = ViewModelProvider(
            this
            ,ViewModelFactory(GamesPlayedDatabase.getInstanceOfDatabase(requireContext()))
        ).get(ViewModelSavedGames::class.java)

        adapterGameStats = RecyclerAdapterDisplayingSavedGames(requireContext(),listOf())
        adapterGameStats.onClickListener = this
        adapterYambTicketToDisplay = RecyclerAdapterForDisplayingYambGame(requireContext(), listOf())
        adapterYambTicketToDisplay.viewModelReference = viewModelSavedGames
        autoFillGridLayoutManager = AutoFillGridLayoutManager(requireContext().applicationContext, ScreenValues.DEVICE_WIDTH.size / ScreenValues.COLUMN_NUMBER.size)

        viewModelSavedGames.getGameStatsFromDatabase()
        recyclerPastGamesList.layoutManager = autoFillGridLayoutManager

        viewModelSavedGames.listOfGames.observe(viewLifecycleOwner, Observer {
            adapterGameStats.changeListOfGameStatsForDisplay(it)
        })

        viewModelSavedGames.listOfItemsInRecyclerForDisplaying.observe(viewLifecycleOwner, Observer {
            adapterYambTicketToDisplay.changeYambGameForDisplay(it)
        })

        viewModelSavedGames.textAboveRecycler.observe(viewLifecycleOwner, Observer {
            tvPastGamesTitle.text = it
        })

        viewModelSavedGames.floatingActionButton.observe(viewLifecycleOwner,Observer{
            if(it == FloatingActionButtton.ENABLED){
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
                    recyclerPastGamesList.adapter = adapterGameStats
                    autoFillGridLayoutManager.columnWidth = 1080
                    autoFillGridLayoutManager.columnWidthChanged = true
                    viewModelSavedGames.changeFloatingActionButtonState(FloatingActionButtton.DISABLED)
                    viewModelSavedGames.changeTextAboveRecycler()
                }
                Adapters.YAMB_GAME -> {
                    recyclerPastGamesList.adapter = adapterYambTicketToDisplay
                    autoFillGridLayoutManager.columnWidth = ScreenValues.DEVICE_WIDTH.size / ScreenValues.COLUMN_NUMBER.size
                    autoFillGridLayoutManager.columnWidthChanged = true

                    viewModelSavedGames.changeFloatingActionButtonState(FloatingActionButtton.ENABLED)
                    viewModelSavedGames.changeTextAboveRecycler()
                }
            }

        })

        viewModelSavedGames.isDatabaseEmpty.observe(viewLifecycleOwner, Observer {
            showEmptyDatabaseNotification(it)
        })

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