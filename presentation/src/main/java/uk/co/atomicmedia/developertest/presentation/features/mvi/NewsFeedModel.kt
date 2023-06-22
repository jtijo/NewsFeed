package uk.co.atomicmedia.developertest.presentation.features.mvi

import uk.co.atomicmedia.developertest.domain.model.sealed.DomainSealedResponse
import uk.co.atomicmedia.developertest.domain.usecases.GetHeadlineListUseCase
import uk.co.atomicmedia.developertest.domain.usecases.GetHeadlineStoryUseCase
import uk.co.atomicmedia.developertest.presentation.boundary.PresentationMapper
import uk.co.atomicmedia.developertest.presentation.model.PresentationHeadlineList
import uk.co.atomicmedia.developertest.presentation.model.PresentationStoryModel
import uk.co.atomicmedia.developertest.presentation.model.sealed.PresentationSealedResponse
import javax.inject.Inject

class NewsFeedModel @Inject constructor(
    private val getHeadlineListUseCase: GetHeadlineListUseCase,
    private val getHeadlineStoryUseCase: GetHeadlineStoryUseCase,
    private val mapper: PresentationMapper
) {
    suspend fun getHeadlineList(): PresentationSealedResponse<PresentationHeadlineList> {
        return when (val response = getHeadlineListUseCase.execute()) {
            is DomainSealedResponse.Success -> {
                response.data?.let {
                    PresentationSealedResponse.Success(
                        PresentationHeadlineList(
                            it.headlineList.map {
                                mapper.mapPresentationHeadlineModel(it)
                            }
                        )
                    )
                } ?: run {
                    PresentationSealedResponse.Success(
                        PresentationHeadlineList(
                            emptyList()
                        )
                    )
                }
            }
            is DomainSealedResponse.Error -> {
                PresentationSealedResponse.Error(
                    response.error?.let {
                        mapper.mapPresentationErrorResponse(it)
                    }
                )
            }
        }
    }
    suspend fun getHeadlineStory(id: String): PresentationSealedResponse<PresentationStoryModel?> {
        return when (val response = getHeadlineStoryUseCase.execute(params = id)) {
            is DomainSealedResponse.Success -> {
                PresentationSealedResponse.Success(
                    response.data?.let {
                        mapper.mapPresentationHeadlineStoryModel(it)
                    }
                )
            }
            is DomainSealedResponse.Error -> {
                PresentationSealedResponse.Error(
                    response.error?.let {
                        mapper.mapPresentationErrorResponse(it)
                    }
                )
            }
        }
    }
}