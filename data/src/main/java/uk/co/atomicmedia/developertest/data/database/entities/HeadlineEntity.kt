package uk.co.atomicmedia.developertest.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import uk.co.atomicmedia.developertest.data.utils.HEADLINE_ENTITY

@Entity(tableName = HEADLINE_ENTITY)
data class HeadlineEntity(
    @PrimaryKey
    val _id: String,
    val title: String,
    val author: String
)
