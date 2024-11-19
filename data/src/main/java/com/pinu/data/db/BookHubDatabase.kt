package com.pinu.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pinu.domain.entities.BookItemDataModel
import com.pinu.domain.entities.dao.BookDao
import kotlin.concurrent.Volatile


@Database(entities = [(BookItemDataModel::class)], version = 1, exportSchema = false)
abstract class BookHubDatabase : RoomDatabase() {

    abstract fun bookDao():BookDao

    companion object {
        /*
        The value of a volatile variable will never be cached, and all writes and reads will be done to and from the main memory.
        This helps make sure the value of INSTANCE is always up-to-date and the same for all execution threads.
        It means that changes made by one thread to INSTANCE are visible to all other threads immediately.
       */
        @Volatile
        private var instance: BookHubDatabase? = null

        fun getDatabase(context: Context): BookHubDatabase {
            return if (instance != null) {
                instance as BookHubDatabase
            } else {
                // only one thread of execution at a time can enter this block of code
                synchronized(this) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BookHubDatabase::class.java, "book-hub-database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    instance as BookHubDatabase
                }
            }

        }
    }

}