package com.example.autofillgridlayoutmanagerapplication.database

import androidx.room.*



@Entity
data class GameStat(

    @PrimaryKey(autoGenerate = true)
    val gameId : Long,
    val date : String,
    val totalPoints : Int
)


