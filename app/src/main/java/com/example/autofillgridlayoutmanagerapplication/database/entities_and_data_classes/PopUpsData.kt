package com.example.autofillgridlayoutmanagerapplication.database.entities_and_data_classes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pop_ups_table")
class PopUpsData(
    @PrimaryKey(autoGenerate = false)
    val id : Int,
    val positionOfItemClicked : Int,
    val totalPoints : Int
)