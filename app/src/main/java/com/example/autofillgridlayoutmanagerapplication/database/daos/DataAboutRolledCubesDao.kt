package com.example.autofillgridlayoutmanagerapplication.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.autofillgridlayoutmanagerapplication.database.entities_and_data_classes.DataAboutRolledCubes
import io.reactivex.Flowable

@Dao
interface DataAboutRolledCubesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData( data : DataAboutRolledCubes)

    @Query("SELECT * FROM cubes_data_table WHERE dataRolledCubesId =:dataId")
    fun getData(dataId : Long) : Flowable<DataAboutRolledCubes>

  /*  @Query("UPDATE cubes_data_table SET isRecyclerFrozen=:isFreezed WHERE dataRolledCubesId=:dataRolledCubesId")
    fun  updateIsRecyclerFreezedState(dataRolledCubesId : Long,isFreezed : Boolean)

    @Query("UPDATE cubes_data_table SET enableButtonForRollingDices=:isButtonEnabled WHERE dataRolledCubesId=:dataRolledCubesId")
    fun  updateIsButtonForRollingCubesEnabled(dataRolledCubesId : Long, isButtonEnabled : Boolean)
    */
}