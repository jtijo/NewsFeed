package uk.co.atomicmedia.developertest.presentation.boundary

import uk.co.atomicmedia.developertest.domain.model.DomainErrorResponse
import uk.co.atomicmedia.developertest.domain.model.DomainHeadlineModel
import uk.co.atomicmedia.developertest.domain.model.DomainStoryModel
import uk.co.atomicmedia.developertest.presentation.model.PresentationErrorResponse
import uk.co.atomicmedia.developertest.presentation.model.PresentationHeadlineModel
import uk.co.atomicmedia.developertest.presentation.model.PresentationStoryModel
import javax.inject.Inject

class PresentationMapper @Inject constructor() {

    fun mapPresentationHeadlineModel(domainHeadlineModel: DomainHeadlineModel): PresentationHeadlineModel {
        return PresentationHeadlineModel(
            id = domainHeadlineModel.id,
            title = domainHeadlineModel.title,
            author = domainHeadlineModel.author
        )
    }

    fun mapPresentationHeadlineStoryModel(domainStoryModel: DomainStoryModel): PresentationStoryModel {
        return PresentationStoryModel(
            id = domainStoryModel.id,
            author = domainStoryModel.author,
            title = domainStoryModel.title,
            content = domainStoryModel.content,
            publishedDate = domainStoryModel.publishedDate
        )
    }

    fun mapPresentationErrorResponse(domainErrorResponse: DomainErrorResponse) : PresentationErrorResponse{
        return PresentationErrorResponse(
            errorMessage = domainErrorResponse.errorMessage
        )
    }
}