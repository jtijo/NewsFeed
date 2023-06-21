package uk.co.atomicmedia.developertest.data.api.rxjava

import io.reactivex.rxjava3.core.Single
import uk.co.atomicmedia.developertest.HAS_CONNECTION
import uk.co.atomicmedia.developertest.data.api.ApiException
import uk.co.atomicmedia.developertest.data.api.dto.StoryDto
import uk.co.atomicmedia.developertest.data.api.dto.HeadlineDto
import uk.co.atomicmedia.developertest.mockStories
import java.util.concurrent.TimeUnit

class RxJavaNewsApi : IRxJavaNewsApi {

    override fun getNewsHeadlines(): Single<List<HeadlineDto>> {
        if (!HAS_CONNECTION) {
            return Single.error<List<HeadlineDto>>(ApiException.NetworkError())
                .delay(500, TimeUnit.MILLISECONDS)
        }

        @Suppress("DEPRECATION")
        return Single.just(mockStories.map {
            HeadlineDto(it.id, it.title, it.author)
        }).delay(500, TimeUnit.MILLISECONDS)
    }

    override fun getNewsStory(id: String): Single<StoryDto> {
        if (!HAS_CONNECTION) {
            return Single.error<StoryDto>(ApiException.NetworkError())
                .delay(500, TimeUnit.MILLISECONDS)
        }

        @Suppress("DEPRECATION")
        val newsDto = mockStories.firstOrNull { it.id == id }

        return if (newsDto == null) {
            Single.error<StoryDto>(ApiException.HttpError(404)).delay(500, TimeUnit.MILLISECONDS)
        } else {
            Single.just(newsDto).delay(500, TimeUnit.MILLISECONDS)
        }
    }
}