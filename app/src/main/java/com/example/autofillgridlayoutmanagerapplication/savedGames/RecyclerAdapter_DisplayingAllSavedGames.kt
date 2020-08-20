package com.example.autofillgridlayoutmanagerapplication.savedGames

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.autofillgridlayoutmanagerapplication.database.EntitiesAndDataCalsses.GameStat
import com.example.autofillgridlayoutmanagerapplication.databinding.PastGamesDataForBinding
import com.example.autofillgridlayoutmanagerapplication.databinding.PastGamesReyclerElementBinding
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.IOnGameClickedListener

class RecyclerAdapter_DisplayingAllSavedGames(
    private val context : Context,
    private var gamesToDisplay : List<GameStat>
) :  RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var onClickListener : IOnGameClickedListener? = null

    inner class PastGamesViewHolder(val databindingLayout : PastGamesReyclerElementBinding) : RecyclerView.ViewHolder(databindingLayout.root) {

        internal fun bind(position: Int){
            databindingLayout.data = PastGamesDataForBinding(
                date = "Date: ${gamesToDisplay[position].date}",
                totalPoints = "Points: ${gamesToDisplay[position].totalPoints}"
            )
            databindingLayout.onClickListener = onClickListener
            databindingLayout.gameId = gamesToDisplay[position].gameId
            databindingLayout.executePendingBindings()
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val databindingLayout = PastGamesReyclerElementBinding.inflate(LayoutInflater.from(context),parent,false)
        return PastGamesViewHolder(databindingLayout)
    }

    override fun getItemCount(): Int {
        return gamesToDisplay.count()
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PastGamesViewHolder).bind(position)
    }

    fun changeListOfGameStatsForDisplay(list : List<GameStat>){
        gamesToDisplay = list
        notifyDataSetChanged()
    }

}