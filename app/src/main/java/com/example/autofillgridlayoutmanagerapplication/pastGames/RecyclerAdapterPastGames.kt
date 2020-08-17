package com.example.autofillgridlayoutmanagerapplication.pastGames

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.autofillgridlayoutmanagerapplication.R
import com.example.autofillgridlayoutmanagerapplication.database.GameStat

class RecyclerAdapterPastGames(
    private val context : Context,
    private var gamesToDisplay : List<GameStat>,
    private val openSavedGameOnClick : (Long) -> Unit
) :  RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    inner class PastGamesViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        private val date = view.findViewById<TextView>(R.id.tvPastGamesElement_Date)
        private val totalPoints = view.findViewById<TextView>(R.id.tvPastGamesElement_TotalPoints)
        private val recyclerElementLayout = view.findViewById<ConstraintLayout>(R.id.recyclerElementLayout)

        internal fun bind(position: Int){
            date.text = "Date: ${gamesToDisplay[position].date}"
            totalPoints.text = "Points: ${gamesToDisplay[position].totalPoints}"

            recyclerElementLayout.setOnClickListener {
                openSavedGameOnClick((gamesToDisplay[position]).gameId)
            }

        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.past_games_reycler_element,parent,false)
        return PastGamesViewHolder(view)
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