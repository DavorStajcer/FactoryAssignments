package com.example.autofillgridlayoutmanagerapplication.savedGames

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.autofillgridlayoutmanagerapplication.R
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.FragmentYamb.Adapters
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.FragmentYamb.RecyclerAndFloatinActionButton
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.recylcerAdapterForDisplayingYambGame.AutoFillGridLayoutManager
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.IOnGameClickedListener
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.ScreenValues
import kotlinx.android.synthetic.main.fragment_past_games.*



class PastGamesFragment  : Fragment() , IOnGameClickedListener {

    private var viewModelSavedGames : ViewModel_FragmentSavedGames? = null
    private var adapterGameStats : RecyclerAdapter_DisplayingAllSavedGames? = null
    private var adapterYambTicketToDisplay : RecyclerAdapter_DisplayingClickedSaveGame? = null
    private var autoFillGridLayoutManager :AutoFillGridLayoutManager? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_past_games,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelSavedGames  = viewModelSavedGames ?: ViewModelProvider(this).get(ViewModel_FragmentSavedGames::class.java)
        adapterGameStats = RecyclerAdapter_DisplayingAllSavedGames(requireContext(),listOf())
        adapterGameStats!!.onClickListener = this
        adapterYambTicketToDisplay = RecyclerAdapter_DisplayingClickedSaveGame(requireContext(),listOf())
        autoFillGridLayoutManager = AutoFillGridLayoutManager(requireContext().applicationContext, ScreenValues.DEVICE_WIDTH.size / ScreenValues.COLUMN_NUMBER.size)


        viewModelSavedGames!!.getGameStatsFromDatabase(requireContext())
        recyclerPastGamesList.layoutManager = autoFillGridLayoutManager


        viewModelSavedGames!!.listOfGames.observe(viewLifecycleOwner, Observer {
            adapterGameStats!!.changeListOfGameStatsForDisplay(it)
        })

        viewModelSavedGames!!.listOfItemsInRecyclerForDisplaying.observe(viewLifecycleOwner, Observer {
            adapterYambTicketToDisplay!!.changeYambGameForDisplay(it)
        })

        viewModelSavedGames!!.textAboveRecycler.observe(viewLifecycleOwner, Observer {
            tvPastGamesTitle.text = it
        })

        viewModelSavedGames!!.floatingActionButton.observe(viewLifecycleOwner,Observer{
            if(it == RecyclerAndFloatinActionButton.ENABLED){
                floatingActionButton_BackToListOfGames.visibility = View.VISIBLE
                setClickListenerForFloatingButton()
            }
            else{
                floatingActionButton_BackToListOfGames.visibility = View.INVISIBLE
                removeClickListenerForFloatingButton()
            }

        })

        viewModelSavedGames!!.currentAdapter.observe(viewLifecycleOwner, Observer {

             when(it!!){
                Adapters.GAME_STATS ->{
                    recyclerPastGamesList.adapter = adapterGameStats
                    autoFillGridLayoutManager!!.columnWidth = 1080
                    autoFillGridLayoutManager!!.columnWidthChanged = true
                    viewModelSavedGames!!.changeFloatingActionButtonState(RecyclerAndFloatinActionButton.DISABLED)
                    viewModelSavedGames!!.changeTextAboveRecycler()
                }
                Adapters.YAMB_GAME -> {
                    recyclerPastGamesList.adapter = adapterYambTicketToDisplay
                    autoFillGridLayoutManager!!.columnWidth = ScreenValues.DEVICE_WIDTH.size / ScreenValues.COLUMN_NUMBER.size
                    autoFillGridLayoutManager!!.columnWidthChanged = true

                    viewModelSavedGames!!.changeFloatingActionButtonState(RecyclerAndFloatinActionButton.ENABLED)
                    viewModelSavedGames!!.changeTextAboveRecycler()
                }
            }

        })

        viewModelSavedGames!!.isDatabaseEmpty.observe(viewLifecycleOwner, Observer {
            showEmptyDatabaseNotification(it)
        })

    }

    private fun setClickListenerForFloatingButton(){
        floatingActionButton_BackToListOfGames.setOnClickListener {
            viewModelSavedGames!!.changeAdapter()
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
        viewModelSavedGames!!.restartAdapters()
        super.onDestroyView()
    }

    override fun showClickedGame(gameId: Long) {
        viewModelSavedGames!!.showClickedGame(gameId)
    }


}