package uk.co.atomicmedia.developertest.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uk.co.atomicmedia.developertest.data.database.entities.HeadlineEntity
import uk.co.atomicmedia.developertest.data.database.entities.StoryEntity
import uk.co.atomicmedia.developertest.data.utils.ROOM_DATABASE_NAME

@Database(entities = [StoryEntity::class, HeadlineEntity::class], version = 1, exportSchema = false)
abstract class NewsFeedDatabase : RoomDatabase() {

    abstract fun getNewsFeedDao(): NewsFeedDao

    companion object {
        private var INSTANCE: NewsFeedDatabase? = null

        fun getNewsFeedDatabase(context: Context): NewsFeedDatabase? {
            if (INSTANCE == null) {
                synchronized(NewsFeedDatabase::class)
                {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        NewsFeedDatabase::class.java,
                        ROOM_DATABASE_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}