package com.example.autofillgridlayoutmanagerapplication.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.autofillgridlayoutmanagerapplication.database.daos.ColumnInYambDao
import com.example.autofillgridlayoutmanagerapplication.database.daos.DataAboutRolledCubesDao
import com.example.autofillgridlayoutmanagerapplication.database.daos.GameStatsDao
import com.example.autofillgridlayoutmanagerapplication.database.daos.PopUpsDao
import com.example.autofillgridlayoutmanagerapplication.database.entities_and_data_classes.ColumnInYamb
import com.example.autofillgridlayoutmanagerapplication.database.entities_and_data_classes.DataAboutRolledCubes
import com.example.autofillgridlayoutmanagerapplication.database.entities_and_data_classes.GameStat
import com.example.autofillgridlayoutmanagerapplication.database.entities_and_data_classes.PopUpsData

@Database(entities = [GameStat::class, ColumnInYamb::class, DataAboutRolledCubes::class, PopUpsData::class], version = 38,exportSchema = true)
abstract class GamesPlayedDatabase : RoomDatabase() {


    abstract fun getGameStatsDao(): GameStatsDao
    abstract fun getColumnDao(): ColumnInYambDao
    abstract fun getDataAboutRolledCubesDao(): DataAboutRolledCubesDao
    abstract fun getPopUpsDataDao() : PopUpsDao

    companion object{

        private var databaseInstance: GamesPlayedDatabase? = null
        fun getInstanceOfDatabase(context: Context): GamesPlayedDatabase {

            databaseInstance = databaseInstance ?: Room.databaseBuilder(
                context.applicationContext,
                GamesPlayedDatabase::class.java,
                "yamb_database"
            ).fallbackToDestructiveMigration().build()
            return databaseInstance as GamesPlayedDatabase
        }

    }


}
