package com.example.autofillgridlayoutmanagerapplication.database

import androidx.room.*
import io.reactivex.Flowable


@Dao
interface ColumnInYambDao {


    @Insert
    fun insertColumns(vararg columnInYamb: ColumnInYamb)


}
