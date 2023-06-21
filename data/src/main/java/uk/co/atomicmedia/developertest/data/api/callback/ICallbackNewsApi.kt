package uk.co.atomicmedia.developertest.data.api.callback

import uk.co.atomicmedia.developertest.data.api.ApiException
import uk.co.atomicmedia.developertest.data.api.dto.StoryDto
import uk.co.atomicmedia.developertest.data.api.dto.HeadlineDto

interface ApiCallback<T> {
    fun onSuccess(data: T)
    fun onFailure(apiException: ApiException)
}

interface ICallbackNewsApi {


    /**
     * Returns a list of news headlines
     * onFailure will be called with an instance of [uk.co.atomicmedia.developertest.data.api.ApiException] on error
     */
    fun getNewsHeadlines(apiCallback: ApiCallback<List<HeadlineDto>>)

    /**
     * Returns a specific news story
     * onFailure will be called with an instance of [uk.co.atomicmedia.developertest.data.api.ApiException] on error
     */
    fun getNewsStory(id: String, apiCallback: ApiCallback<StoryDto>)
}