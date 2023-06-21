package uk.co.atomicmedia.developertest.data.repository

import uk.co.atomicmedia.developertest.data.api.ApiException
import uk.co.atomicmedia.developertest.data.api.coroutines.ICoroutineNewsApi
import uk.co.atomicmedia.developertest.data.boundary.DataMapper
import uk.co.atomicmedia.developertest.data.database.NewsFeedDao
import uk.co.atomicmedia.developertest.data.utils.*
import uk.co.atomicmedia.developertest.domain.model.DomainErrorResponse
import uk.co.atomicmedia.developertest.domain.model.DomainHeadlineList
import uk.co.atomicmedia.developertest.domain.model.DomainStoryModel
import uk.co.atomicmedia.developertest.domain.model.sealed.DomainSealedResponse
import uk.co.atomicmedia.developertest.domain.repositorycontracts.DomainNewsRepositoryContract
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val newsApi: ICoroutineNewsApi,
    private val newsFeedDao: NewsFeedDao,
    private val mapper: DataMapper
) : DomainNewsRepositoryContract {
    override suspend fun getHeadLineList(): DomainSealedResponse<DomainHeadlineList> {
        try {
            val newsHeadlines = newsApi.getNewsHeadlines().map {
                mapper.mapHeadlineEntity(it)
            }
            newsFeedDao.insertHeadlines(newsHeadlines)
            return DomainSealedResponse.Success(
                getHeadlineListFromLocalDb()
            )
        } catch (exception: Exception) {
            if (getHeadlineListFromLocalDb().headlineList.isNotEmpty()) {
                return DomainSealedResponse.Success(
                    getHeadlineListFromLocalDb()
                )
            }
            return DomainSealedResponse.Error(handleException(exception = exception))
        }
    }

    override suspend fun getHeadlineStory(id: String): DomainSealedResponse<DomainStoryModel?> {
        try {
            val headlineStory = newsApi.getNewsStory(id = id)
            newsFeedDao.insertStory(mapper.mapStoryEntity(headlineStory))
            return DomainSealedResponse.Success(getHeadlineStoryFromLocalDb(id = id))
        } catch (exception: Exception) {
            getHeadlineStoryFromLocalDb(id = id)?.let {
                return DomainSealedResponse.Success(getHeadlineStoryFromLocalDb(id = id))
            }
            return DomainSealedResponse.Error(handleException(exception = exception))
        }
    }

    private suspend fun getHeadlineListFromLocalDb(): DomainHeadlineList {
        val headlineList = newsFeedDao.getHeadlinesList().map {
            mapper.mapDomainHeadlineModel(it)
        }
        return DomainHeadlineList(headlineList = headlineList)
    }

    private suspend fun getHeadlineStoryFromLocalDb(id: String): DomainStoryModel? {
        val storyEntity = newsFeedDao.getHeadlineStory(headline_id = id)
        return storyEntity?.let { mapper.mapDomainStoryModel(it) }
    }

    private suspend fun handleException(exception: Exception): DomainErrorResponse {
        return when (exception) {
            is ApiException.HttpError -> {
                DomainErrorResponse(
                    errorCode = ERROR_CODE_HTTP_ERROR,
                    errorMessage = ERROR_MSG_HTTP_ERROR
                )
            }
            is ApiException.NetworkError -> {
                DomainErrorResponse(
                    errorCode = ERROR_CODE_NETWORK,
                    errorMessage = ERROR_MSG_NO_NETWORK
                )
            }
            else -> {
                DomainErrorResponse(
                    errorCode = ERROR_CODE_UNSPECIFIED_ERROR,
                    errorMessage = ERROR_MSG
                )
            }
        }
    }

}