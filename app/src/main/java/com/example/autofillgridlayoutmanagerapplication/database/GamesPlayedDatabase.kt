package com.example.autofillgridlayoutmanagerapplication.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.autofillgridlayoutmanagerapplication.database.daos.ColumnInYambDao
import com.example.autofillgridlayoutmanagerapplication.database.daos.DataAboutRolledCubesDao
import com.example.autofillgridlayoutmanagerapplication.database.daos.GameStatsDao
import com.example.autofillgridlayoutmanagerapplication.database.entities_and_data_classes.ColumnInYamb
import com.example.autofillgridlayoutmanagerapplication.database.entities_and_data_classes.DataAboutRolledCubes
import com.example.autofillgridlayoutmanagerapplication.database.entities_and_data_classes.GameStat

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
