package uk.co.atomicmedia.developertest.data.database

import androidx.room.*
import uk.co.atomicmedia.developertest.data.database.entities.HeadlineEntity
import uk.co.atomicmedia.developertest.data.database.entities.StoryEntity
import uk.co.atomicmedia.developertest.data.utils.RoomDbConstants.HEADLINE_ENTITY
import uk.co.atomicmedia.developertest.data.utils.RoomDbConstants.STORY_ENTITY

@Dao
interface NewsFeedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertHeadlines(headlineList: List<HeadlineEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertStory(story: StoryEntity)

    @Query("SELECT * FROM $HEADLINE_ENTITY")
    @Transaction
    suspend fun getHeadlinesList(): List<HeadlineEntity>

    @Query("SELECT * FROM $STORY_ENTITY WHERE headline_id = :headline_id")
    @Transaction
    suspend fun getHeadlineStory(headline_id: String): StoryEntity?
}