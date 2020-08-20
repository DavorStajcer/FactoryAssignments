package com.example.autofillgridlayoutmanagerapplication.database.Daos

import androidx.room.*
import com.example.autofillgridlayoutmanagerapplication.database.EntitiesAndDataCalsses.ColumnInYamb


@Dao
interface ColumnInYambDao {


    @Insert
    fun insertColumns(vararg columnInYamb: ColumnInYamb)


}
