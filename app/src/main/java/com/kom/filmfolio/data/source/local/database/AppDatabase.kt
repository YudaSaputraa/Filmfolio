package com.kom.filmfolio.data.source.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kom.filmfolio.data.source.local.database.dao.FavDao
import com.kom.filmfolio.data.source.local.database.entity.FavouriteEntity

@Database(
    entities = [FavouriteEntity::class],
    version = 1,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): FavDao

    companion object {
        private const val DB_NAME = "filmfolio.db"

        fun createInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DB_NAME,
            ).fallbackToDestructiveMigration().build()
        }
    }
}
