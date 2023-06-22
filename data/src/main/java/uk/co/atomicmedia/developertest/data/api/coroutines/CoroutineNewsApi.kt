package uk.co.atomicmedia.developertest.data.api.coroutines

import kotlinx.coroutines.delay
import uk.co.atomicmedia.developertest.data.HAS_CONNECTION
import uk.co.atomicmedia.developertest.data.api.ApiException
import uk.co.atomicmedia.developertest.data.api.dto.StoryDto
import uk.co.atomicmedia.developertest.data.api.dto.HeadlineDto
import uk.co.atomicmedia.developertest.data.mockStories
import javax.inject.Inject

class CoroutineNewsApi @Inject constructor(): ICoroutineNewsApi {

    override suspend fun getNewsHeadlines(): List<HeadlineDto> {
        delay(500)
        checkConnection()

        @Suppress("DEPRECATION")
        return mockStories.map {
            HeadlineDto(it.id, it.title, it.author)
        }
    }

    override suspend fun getNewsStory(id: String): StoryDto {
        delay(500)
        checkConnection()

        @Suppress("DEPRECATION")
        return mockStories.firstOrNull { it.id == id } ?: throw ApiException.HttpError(404)
    }

    private fun checkConnection() {
        if (!HAS_CONNECTION) {
            throw ApiException.NetworkError()
        }
    }
}