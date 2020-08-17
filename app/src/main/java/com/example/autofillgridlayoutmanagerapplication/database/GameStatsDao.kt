package com.example.autofillgridlayoutmanagerapplication.database

import androidx.room.*
import io.reactivex.Flowable


@Dao
interface GameStatsDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGameStats(gameStat : GameStat) : Long


    @Query("SELECT * FROM GameStat")
    fun getAllGameStats() : Flowable<List<GameStat>>


    @Query("SELECT * FROM GameStat WHERE gameId=:gameID")
    fun getGameStat(gameID: Long) : Flowable<GameStat>


    @Transaction
    @Query("SELECT * FROM GameStat WHERE gameId =:gameID")
    fun getGameStatsWithCorrespondingColumns(gameID : Long) : Flowable<GameStatsWithCorrespondingColumns>


}
