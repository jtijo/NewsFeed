package uk.co.atomicmedia.developertest.data.boundary

import uk.co.atomicmedia.developertest.data.api.dto.HeadlineDto
import uk.co.atomicmedia.developertest.data.api.dto.StoryDto
import uk.co.atomicmedia.developertest.data.database.entities.HeadlineEntity
import uk.co.atomicmedia.developertest.data.database.entities.StoryEntity
import uk.co.atomicmedia.developertest.data.utils.convertToDateString
import uk.co.atomicmedia.developertest.domain.model.DomainHeadlineModel
import uk.co.atomicmedia.developertest.domain.model.DomainStoryModel
import javax.inject.Inject

class DataMapper @Inject constructor() {
    fun mapHeadlineEntity(headlineDto: HeadlineDto): HeadlineEntity {
        return HeadlineEntity(
            _id = headlineDto.id,
            title = headlineDto.title,
            author = headlineDto.author
        )
    }
    fun mapDomainHeadlineModel(headlineEntity: HeadlineEntity): DomainHeadlineModel {
        return DomainHeadlineModel(
            id = headlineEntity._id,
            title = headlineEntity.title,
            author = headlineEntity.author
        )
    }
    fun mapStoryEntity(storyDto: StoryDto): StoryEntity {
        return StoryEntity(
            headline_id = storyDto.id,
            author = storyDto.author,
            title = storyDto.title,
            content = storyDto.content,
            published_date = storyDto.publishedAt.convertToDateString()
        )
    }
    fun mapDomainStoryModel(storyEntity: StoryEntity): DomainStoryModel {
        return DomainStoryModel(
            id = storyEntity.headline_id,
            author = storyEntity.author,
            title = storyEntity.title,
            content = storyEntity.content,
            publishedDate = storyEntity.published_date
        )
    }
}