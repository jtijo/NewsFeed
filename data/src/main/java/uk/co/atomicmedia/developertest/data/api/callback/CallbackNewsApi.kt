package uk.co.atomicmedia.developertest.data.api.callback

import uk.co.atomicmedia.developertest.HAS_CONNECTION
import uk.co.atomicmedia.developertest.data.api.ApiException
import uk.co.atomicmedia.developertest.data.api.dto.StoryDto
import uk.co.atomicmedia.developertest.data.api.dto.HeadlineDto
import uk.co.atomicmedia.developertest.mockStories
import kotlin.concurrent.thread

class CallbackNewsApi : ICallbackNewsApi {

    override fun getNewsHeadlines(apiCallback: ApiCallback<List<HeadlineDto>>) {
        thread {
            Thread.sleep(500)

            if (!HAS_CONNECTION) {
                apiCallback.onFailure(ApiException.NetworkError())
                return@thread
            }

            @Suppress("DEPRECATION")
            apiCallback.onSuccess(mockStories.map { HeadlineDto(it.id, it.title, it.author) })
        }
    }

    override fun getNewsStory(id: String, apiCallback: ApiCallback<StoryDto>) {
        thread {
            Thread.sleep(500)

            if (!HAS_CONNECTION) {
                apiCallback.onFailure(ApiException.NetworkError())
                return@thread
            }

            @Suppress("DEPRECATION")
            val newsDto = mockStories.firstOrNull { it.id == id }

            if (newsDto == null) {
                apiCallback.onFailure(ApiException.HttpError(404))
            } else {
                apiCallback.onSuccess(newsDto)
            }
        }
    }
}