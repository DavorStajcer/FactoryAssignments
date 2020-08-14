package com.example.autofillgridlayoutmanagerapplication.database

import androidx.room.*
import java.util.*


/*


@Entity(tableName = "game_stats")
data class GameStats(

    @PrimaryKey(autoGenerate = true)
    val statId : Int,
    val yambTickedId: YambTicket,
    val date : String,
    val totalPoints : Int
)


@Entity(tableName = "yamb_ticket")
data class YambTicket(

    @PrimaryKey(autoGenerate = true)
    val ticketId : Int,

    @Relation(
        parentColumn = "ticketId",
        entityColumn = "yambTickedId"
    )
    val gameStatId : GameStats,

    @Relation(
        parentColumn = "tickedId",
        entityColumn = "yambTickedId")
    val ticketColumns : List<ColumnInYamb>
)


@Entity(tableName = "column_in_yamb")
data class  ColumnInYamb (

    @PrimaryKey(autoGenerate = true)
    val columnId :Int,
    val yambTickedId : Int,
    val one : Int,
    val two : Int,
    val three : Int,
    val four : Int,
    val five : Int,
    val six : Int,
    val sum_form_one_to_six : Int,
    val max : Int,
    val min : Int,
    val max_min : Int,
    val two_pairs : Int,
    val straight : Int,
    val full : Int,
    val poker : Int,
    val yamb : Int,
    val sum_from_two_pairs_to_yamb : Int

) */

