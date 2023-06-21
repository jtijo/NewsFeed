package uk.co.atomicmedia.developertest.data.api.coroutines

import uk.co.atomicmedia.developertest.data.api.dto.StoryDto
import uk.co.atomicmedia.developertest.data.api.dto.HeadlineDto

interface ICoroutineNewsApi {

    /**
     * Returns a list of news headlines
     * Throws a [uk.co.atomicmedia.developertest.data.api.ApiException] on error
     */
    suspend fun getNewsHeadlines(): List<HeadlineDto>

    /**
     * Returns a specific news story
     * Throws a [uk.co.atomicmedia.developertest.data.api.ApiException] on error
     */
    suspend fun getNewsStory(id: String): StoryDto
}