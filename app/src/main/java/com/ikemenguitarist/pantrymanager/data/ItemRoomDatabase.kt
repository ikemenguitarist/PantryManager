package com.ikemenguitarist.pantrymanager.data

import android.content.Context
import androidx.room.*
import java.util.*

@Database(entities = [Item::class,Stocker::class,Recipe::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ItemRoomDatabase:RoomDatabase() {
    abstract fun itemDao():ItemDao
    companion object{
        @Volatile
        private var INSTANCE:ItemRoomDatabase? = null

        fun getDatabase(context: Context):ItemRoomDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,ItemRoomDatabase::class.java,"item_database")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }

    }
}
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
