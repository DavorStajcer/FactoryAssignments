package com.example.autofillgridlayoutmanagerapplication.database.daos

import androidx.room.*
import com.example.autofillgridlayoutmanagerapplication.database.entities_and_data_classes.ColumnInYamb


@Dao
interface ColumnInYambDao {

    @Insert
    fun insertColumns(vararg columnInYamb: ColumnInYamb)

}
