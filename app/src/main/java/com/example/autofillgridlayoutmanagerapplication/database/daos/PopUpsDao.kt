package com.example.autofillgridlayoutmanagerapplication.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.autofillgridlayoutmanagerapplication.database.entities_and_data_classes.PopUpsData
import io.reactivex.Flowable

@Dao
interface PopUpsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPopUpsData(popUpsData: PopUpsData)

    @Query("SELECT * FROM pop_ups_table WHERE id =:popUpId")
    fun getPopUpsData(popUpId : Int) : Flowable<PopUpsData>

}