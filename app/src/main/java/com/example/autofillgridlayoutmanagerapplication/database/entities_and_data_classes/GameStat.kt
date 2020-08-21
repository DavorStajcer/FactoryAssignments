package com.example.autofillgridlayoutmanagerapplication.database.entities_and_data_classes

import androidx.room.*



@Entity
data class GameStat(

    @PrimaryKey(autoGenerate = true)
    val gameId : Long,
    val date : String,
    val totalPoints : Int
)


