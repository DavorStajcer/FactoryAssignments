package com.example.autofillgridlayoutmanagerapplication.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GameStat::class, ColumnInYamb::class], version = 21,exportSchema = true)
abstract class GamesPlayedDatabase : RoomDatabase() {


   abstract fun getGameStatsDao(): GameStatsDao
   abstract fun getColumnDao(): ColumnInYambDao

    companion object{

        private var databaseInstance: GamesPlayedDatabase? = null
        fun getInstanceOfDatabase(context: Context): GamesPlayedDatabase {

            databaseInstance = databaseInstance ?: Room.databaseBuilder(
                context.applicationContext,
                GamesPlayedDatabase::class.java,
                "note_databse"
            ).fallbackToDestructiveMigration().build()
            return databaseInstance as GamesPlayedDatabase
        }

    }


}
