package com.example.autofillgridlayoutmanagerapplication.pastGames

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.autofillgridlayoutmanagerapplication.R
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.recylcer.AutoFillGridLayoutManager
import com.example.autofillgridlayoutmanagerapplication.displaying_yamb_ticket.recylcer.RecyclerAdapterForDisplayingYambGame
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.ScreenValues
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.past_games_list.*


class PastGamesFragment  : Fragment() {

    private var viewModel : PastGamesViewModel? = null
    private var adapterGameStats : RecyclerAdapterPastGames? = null
    private var adapterYambTicketToDisplay : RecyclerAdapterForDisplayingYambGame? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.past_games_list,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel  = viewModel ?: ViewModelProvider(this).get(PastGamesViewModel::class.java)
        adapterGameStats = RecyclerAdapterPastGames(requireContext(),listOf(),viewModel!!::showClickedGame)
        adapterYambTicketToDisplay = RecyclerAdapterForDisplayingYambGame(requireContext(),listOf(),viewModel!!::doNothingWhenRecyclcerAdapterIsClicked)
        val autoFillGridLayoutManager = AutoFillGridLayoutManager(requireContext().applicationContext, ScreenValues.DEVICE_WIDTH.size / ScreenValues.COLUMN_NUMBER.size)

        viewModel!!.getGameStatsFromDatabase(requireContext())
        recyclerPastGamesList.layoutManager = autoFillGridLayoutManager


        viewModel!!.listOfGames.observe(viewLifecycleOwner, Observer {
            adapterGameStats!!.changeListOfGameStatsForDisplay(it)
        })

        viewModel!!.listOfItemsInRecyclerForDisplaying.observe(viewLifecycleOwner, Observer {
            adapterYambTicketToDisplay!!.changeYambGameForDisplay(it)
        })

        viewModel!!.textAboveRecycler.observe(viewLifecycleOwner, Observer {
            tvPastGamesTitle.text = it
        })

        viewModel!!.currentAdapter.observe(viewLifecycleOwner, Observer {
             when(it!!){
                Adapters.GAME_STATS ->{
                    autoFillGridLayoutManager.columnWidth = 1080
                    autoFillGridLayoutManager.columnWidthChanged = true
                    recyclerPastGamesList.adapter = adapterGameStats
                }
                Adapters.YAMB_GAME -> {
                    recyclerPastGamesList.adapter = adapterYambTicketToDisplay
                    autoFillGridLayoutManager.columnWidth = ScreenValues.DEVICE_WIDTH.size / ScreenValues.COLUMN_NUMBER.size
                    autoFillGridLayoutManager.columnWidthChanged = true

                }
            }

        })

    }

    override fun onDetach() {
        setStartingAdapterAndText()
        super.onDetach()
    }


    private fun setStartingAdapterAndText(){
        viewModel!!.changeAdapter(Adapters.GAME_STATS)
        viewModel!!.changeTextAboveRecycler("PAST GAMES")
    }


}