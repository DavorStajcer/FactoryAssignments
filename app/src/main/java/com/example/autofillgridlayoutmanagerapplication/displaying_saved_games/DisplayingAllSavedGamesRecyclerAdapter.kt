package com.example.autofillgridlayoutmanagerapplication.displaying_saved_games

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.autofillgridlayoutmanagerapplication.R
import com.example.autofillgridlayoutmanagerapplication.database.entities_and_data_classes.GameStat
import com.example.autofillgridlayoutmanagerapplication.databinding.DisplayingSavedGamesBindingData
import com.example.autofillgridlayoutmanagerapplication.databinding.SavedGamesReyclerElementBinding
import com.example.autofillgridlayoutmanagerapplication.enums_and_interfaces.IOnGameClickedListener

class DisplayingAllSavedGamesRecyclerAdapter(
    private val context : Context,
    private var gamesToDisplay : List<GameStat>
) :  RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var onClickListener : IOnGameClickedListener? = null

    inner class PastGamesViewHolder(val databindingLayout : SavedGamesReyclerElementBinding) : RecyclerView.ViewHolder(databindingLayout.root) {

        internal fun bind(position: Int){
            databindingLayout.data = DisplayingSavedGamesBindingData(
                date = context.getString(R.string.game_stat_date,gamesToDisplay[position].date),
                totalPoints = context.getString(R.string.game_stat_points,gamesToDisplay[position].totalPoints.toString())
            )
            databindingLayout.onClickListener = onClickListener
            databindingLayout.gameId = gamesToDisplay[position].gameId
            databindingLayout.executePendingBindings()
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val databindingLayout = SavedGamesReyclerElementBinding.inflate(LayoutInflater.from(context),parent,false)
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