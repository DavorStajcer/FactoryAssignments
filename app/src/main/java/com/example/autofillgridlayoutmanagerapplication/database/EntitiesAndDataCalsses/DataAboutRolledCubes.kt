package com.example.autofillgridlayoutmanagerapplication.database.EntitiesAndDataCalsses

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cubes_data_table")
class DataAboutRolledCubes(

    @PrimaryKey(autoGenerate = false)
    val dataRolledCubesId : Long,
    @Embedded
    val cubes : Cubes,
    val aheadCall : Boolean,
    val isRecyclerFrozen : Boolean,
    val enableButtonForRollingDices : Boolean

)



data class Cubes(
    val cubeOne: Int,
    val cubeTwo: Int,
    val cubeThree: Int,
    val cubeFour: Int,
    val cubeFive: Int,
    val cubeSix: Int
)


