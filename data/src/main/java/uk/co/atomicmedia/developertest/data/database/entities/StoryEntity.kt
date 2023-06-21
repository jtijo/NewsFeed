package uk.co.atomicmedia.developertest.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import uk.co.atomicmedia.developertest.data.utils.RoomDbConstants.STORY_ENTITY

@Entity(
    tableName = STORY_ENTITY,
    foreignKeys = arrayOf(
        ForeignKey(
            entity = HeadlineEntity::class,
            parentColumns = arrayOf("_id"),
            childColumns = arrayOf("headline_id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ),
    indices = arrayOf(Index(value = ["headline_id"]))
)
data class StoryEntity(
    @PrimaryKey
    val headline_id: String,
    val author: String,
    val title: String,
    val content: String,
    val published_date: String
)
