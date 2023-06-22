package uk.co.atomicmedia.developertest.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import uk.co.atomicmedia.developertest.data.utils.STORY_ENTITY

@Entity(
    tableName = STORY_ENTITY
)
data class StoryEntity(
    @PrimaryKey
    val headline_id: String,
    val author: String,
    val title: String,
    val content: String,
    val published_date: String
)
