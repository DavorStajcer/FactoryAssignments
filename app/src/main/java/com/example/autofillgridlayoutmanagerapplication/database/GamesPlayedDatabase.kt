package com.example.autofillgridlayoutmanagerapplication.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.autofillgridlayoutmanagerapplication.database.Daos.ColumnInYambDao
import com.example.autofillgridlayoutmanagerapplication.database.Daos.DataAboutRolledCubesDao
import com.example.autofillgridlayoutmanagerapplication.database.Daos.GameStatsDao
import com.example.autofillgridlayoutmanagerapplication.database.EntitiesAndDataCalsses.ColumnInYamb
import com.example.autofillgridlayoutmanagerapplication.database.EntitiesAndDataCalsses.DataAboutRolledCubes
import com.example.autofillgridlayoutmanagerapplication.database.EntitiesAndDataCalsses.GameStat

@Database(entities = [GameStat::class, ColumnInYamb::class, DataAboutRolledCubes::class], version = 26,exportSchema = true)
abstract class GamesPlayedDatabase : RoomDatabase() {


   abstract fun getGameStatsDao(): GameStatsDao
   abstract fun getColumnDao(): ColumnInYambDao
   abstract fun getDataAboutRolledCubesDao(): DataAboutRolledCubesDao

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
