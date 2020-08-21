

package com.example.autofillgridlayoutmanagerapplication.database.entities_and_data_classes

import androidx.room.Embedded
import androidx.room.Relation

class GameStatsWithCorrespondingColumns{
    @Embedded
    lateinit var gameStat : GameStat

    @Relation(
        parentColumn = "gameId",
        entityColumn = "correspondingGameId",
        entity = ColumnInYamb::class
    )
    lateinit var  columns : List<ColumnInYamb>

}





