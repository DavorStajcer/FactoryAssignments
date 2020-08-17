package com.example.autofillgridlayoutmanagerapplication.database

import androidx.room.*


@Entity
data class  ColumnInYamb (

    @PrimaryKey(autoGenerate = true)
    val columnId :Int,
    val correspondingGameId : Long,
    val one : Int?,
    val two : Int?,
    val three : Int?,
    val four : Int?,
    val five : Int?,
    val six : Int?,
    val sum_form_one_to_six : Int?,
    val max : Int?,
    val min : Int?,
    val max_min : Int?,
    val two_pairs : Int?,
    val straight : Int?,
    val full : Int?,
    val poker : Int?,
    val yamb : Int?,
    val sum_from_two_pairs_to_yamb : Int?

)
