package uk.co.atomicmedia.developertest.data.api.rxjava

import io.reactivex.rxjava3.core.Single
import uk.co.atomicmedia.developertest.data.api.dto.StoryDto
import uk.co.atomicmedia.developertest.data.api.dto.HeadlineDto

interface IRxJavaNewsApi {
    /**
     * Returns a list of news headlines
     * Can error with a [uk.co.atomicmedia.developertest.data.api.ApiException]
     */
    fun getNewsHeadlines(): Single<List<HeadlineDto>>

    /**
     * Returns a specific news story
     * Can error with a [uk.co.atomicmedia.developertest.data.api.ApiException] on error
     */
    fun getNewsStory(id: String): Single<StoryDto>
}