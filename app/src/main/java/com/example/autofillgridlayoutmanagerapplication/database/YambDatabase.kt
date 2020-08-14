package com.example.autofillgridlayoutmanagerapplication.database

/*
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [YambTicket::class,GameStats::class,ColumnInYamb::class],version = 1)
abstract class YambDatabase : RoomDatabase(){


    private var noteDatabaseInstance : YambDatabase? = null

    abstract fun getGameStatsDao(): GameStatsDao
    abstract fun getColumnDao(): ColumnDao
    abstract fun getYambTicketDao(): YambTicketDao


    fun  getInstanceOfDatabase(context: Context) : YambDatabase{
        if(noteDatabaseInstance == null)
            noteDatabaseInstance = Room.databaseBuilder(context.applicationContext,YambDatabase::class.java,"note_databse").fallbackToDestructiveMigration().build()
        return noteDatabaseInstance as YambDatabase
    }


}*/
