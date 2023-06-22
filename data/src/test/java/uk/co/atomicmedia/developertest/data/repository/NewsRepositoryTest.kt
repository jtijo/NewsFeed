package uk.co.atomicmedia.developertest.data.repository

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import uk.co.atomicmedia.developertest.data.api.ApiException
import uk.co.atomicmedia.developertest.data.api.coroutines.ICoroutineNewsApi
import uk.co.atomicmedia.developertest.data.api.dto.HeadlineDto
import uk.co.atomicmedia.developertest.data.api.dto.StoryDto
import uk.co.atomicmedia.developertest.data.boundary.DataMapper
import uk.co.atomicmedia.developertest.data.database.NewsFeedDao
import uk.co.atomicmedia.developertest.data.database.entities.HeadlineEntity
import uk.co.atomicmedia.developertest.data.database.entities.StoryEntity
import uk.co.atomicmedia.developertest.data.utils.ERROR_CODE_NETWORK
import uk.co.atomicmedia.developertest.domain.model.DomainHeadlineList
import uk.co.atomicmedia.developertest.domain.model.DomainHeadlineModel
import uk.co.atomicmedia.developertest.domain.model.DomainStoryModel
import uk.co.atomicmedia.developertest.domain.model.sealed.DomainSealedResponse
import java.time.Instant

@RunWith(JUnit4::class)
class NewsRepositoryTest {
    private lateinit var newsRepository: NewsRepository
    private lateinit var mockNewsApi: ICoroutineNewsApi
    private lateinit var mockNewsFeedDao: NewsFeedDao
    private lateinit var mockDataMapper: DataMapper
    private lateinit var mockScope: TestScope


    @Before
    fun setUp() {
        mockNewsApi = mockk(relaxed = true)
        mockNewsFeedDao = mockk(relaxed = true)
        mockDataMapper = mockk(relaxed = true)

        val dispatcher = UnconfinedTestDispatcher()
        mockScope = TestScope(dispatcher)
        newsRepository = NewsRepository(
            newsApi = mockNewsApi,
            newsFeedDao = mockNewsFeedDao,
            mapper = mockDataMapper
        )
    }

    @Test
    fun getHeadlineListWithSuccess() = runTest {
        val mockHeadlineDto = listOf(
            HeadlineDto(
                "1",
                "New AI technology can read your mind and predict your future actions",
                "Ethan Jackson"
            ), HeadlineDto(
                "2",
                "Self-driving cars now have the ability to make moral decisions",
                "Daniel O'Sullivan"
            )
        )

        val mockHeadlineListEntity = listOf(
            HeadlineEntity(
                "1",
                "New AI technology can read your mind and predict your future actions",
                "Ethan Jackson"
            ), HeadlineEntity(
                "2",
                "Self-driving cars now have the ability to make moral decisions",
                "Daniel O'Sullivan"
            )
        )
        coEvery { mockNewsApi.getNewsHeadlines() } returns mockHeadlineDto
        mockHeadlineDto.map {
            every { mockDataMapper.mapHeadlineEntity(it) } returns HeadlineEntity(
                _id = it.id,
                author = it.author,
                title = it.title
            )
        }
        coEvery { mockNewsFeedDao.insertHeadlines(mockHeadlineListEntity) } returns Unit
        coEvery { mockNewsFeedDao.getHeadlinesList() } returns mockHeadlineListEntity
        mockHeadlineListEntity.map {
            every { mockDataMapper.mapDomainHeadlineModel(it) } returns DomainHeadlineModel(
                id = it._id,
                title = it.title,
                author = it.author
            )
        }
        var result: DomainSealedResponse<DomainHeadlineList>? = null
        mockScope.launch {
            result = newsRepository.getHeadLineList()
        }
        assertTrue(result is DomainSealedResponse.Success)
        assertTrue(result?.data != null && result?.data?.headlineList?.size!! > 0)
    }

    @Test
    fun getOfflineHeadlineList() = runTest {
        val networkException = mockk<ApiException.NetworkError>(relaxed = true)
        coEvery { mockNewsApi.getNewsHeadlines() } throws networkException
        val mockHeadlineListEntity = listOf(
            HeadlineEntity(
                "1",
                "New AI technology can read your mind and predict your future actions",
                "Ethan Jackson"
            ), HeadlineEntity(
                "2",
                "Self-driving cars now have the ability to make moral decisions",
                "Daniel O'Sullivan"
            )
        )
        coEvery { mockNewsFeedDao.getHeadlinesList() } returns mockHeadlineListEntity
        mockHeadlineListEntity.map {
            every { mockDataMapper.mapDomainHeadlineModel(it) } returns DomainHeadlineModel(
                id = it._id,
                title = it.title,
                author = it.author
            )
        }
        var result: DomainSealedResponse<DomainHeadlineList>? = null
        mockScope.launch {
            result = newsRepository.getHeadLineList()
        }
        assertTrue(result is DomainSealedResponse.Success)
        assertTrue(result?.data != null && result?.data?.headlineList?.size!! > 0)
    }

    @Test
    fun hasFoundNoOfflineDataWithNetworkException() = runTest {
        val networkException = mockk<ApiException.NetworkError>(relaxed = true)
        coEvery { mockNewsApi.getNewsHeadlines() } throws networkException
        val mockHeadlineListEntity = emptyList<HeadlineEntity>()
        coEvery { mockNewsFeedDao.getHeadlinesList() } returns mockHeadlineListEntity
        mockHeadlineListEntity.map {
            every { mockDataMapper.mapDomainHeadlineModel(it) } returns DomainHeadlineModel(
                id = it._id,
                title = it.title,
                author = it.author
            )
        }
        var result: DomainSealedResponse<DomainHeadlineList>? = null
        mockScope.launch {
            result = newsRepository.getHeadLineList()
        }
        assertTrue(result is DomainSealedResponse.Error)
        assertTrue(result?.error != null && result?.error?.errorCode == ERROR_CODE_NETWORK)
    }

    @Test
    fun getHeadlineStoryWithSuccess() = runTest {
        val mockHeadlineId = "1"
        val mockStoryDto =
            StoryDto(
                "1",
                "New AI technology can read your mind and predict your future actions",
                "Ethan Jackson",
                "In a breakthrough in AI technology, a new program has been developed that can read a person's thoughts and predict their future actions. The program, created by a team of researchers at a leading technology company, uses advanced neural imaging techniques to scan the brain and interpret its activity. The implications of this technology are vast, with potential applications in everything from crime prevention to personalized healthcare. However, concerns have been raised over the potential for misuse of this technology and the potential invasion of privacy.",
                Instant.ofEpochMilli(1613568189780L),
            )
        val mockStoryEntity =
            StoryEntity(
                headline_id = "1",
                author = "Ethan Jackson",
                title = "New AI technology can read your mind and predict your future actions",
                content = "In a breakthrough in AI technology, a new program has been developed that can read a person's thoughts and predict their future actions. The program, created by a team of researchers at a leading technology company, uses advanced neural imaging techniques to scan the brain and interpret its activity. The implications of this technology are vast, with potential applications in everything from crime prevention to personalized healthcare. However, concerns have been raised over the potential for misuse of this technology and the potential invasion of privacy.",
                published_date = "12 Feb, 2021 13:39"
            )
        val mockDomainStoryModel = DomainStoryModel(
            id = "1",
            author = "Ethan Jackson",
            title = "New AI technology can read your mind and predict your future actions",
            content = "In a breakthrough in AI technology, a new program has been developed that can read a person's thoughts and predict their future actions. The program, created by a team of researchers at a leading technology company, uses advanced neural imaging techniques to scan the brain and interpret its activity. The implications of this technology are vast, with potential applications in everything from crime prevention to personalized healthcare. However, concerns have been raised over the potential for misuse of this technology and the potential invasion of privacy.",
            publishedDate = "12 Feb, 2021 13:39"
        )

        coEvery { mockNewsApi.getNewsStory(mockHeadlineId) } returns mockStoryDto
        every { mockDataMapper.mapStoryEntity(mockStoryDto) } returns mockStoryEntity
        coEvery { mockNewsFeedDao.insertStory(mockStoryEntity) } returns Unit
        coEvery { mockNewsFeedDao.getHeadlineStory(headline_id = mockHeadlineId) } returns mockStoryEntity
        every { mockDataMapper.mapDomainStoryModel(mockStoryEntity) } returns mockDomainStoryModel
        var result: DomainSealedResponse<DomainStoryModel?>? = null
        mockScope.launch {
            result = newsRepository.getHeadlineStory(id = mockHeadlineId)
        }
        assertTrue(result is DomainSealedResponse.Success)
        assertTrue(result?.data != null && result?.data != null)
    }

    @Test
    fun getOfflineHeadlineStory() = runTest {
        val networkException = mockk<ApiException.NetworkError>(relaxed = true)
        val mockHeadlineId = "1"
        val mockStoryEntity =
            StoryEntity(
                headline_id = "1",
                author = "Ethan Jackson",
                title = "New AI technology can read your mind and predict your future actions",
                content = "In a breakthrough in AI technology, a new program has been developed that can read a person's thoughts and predict their future actions. The program, created by a team of researchers at a leading technology company, uses advanced neural imaging techniques to scan the brain and interpret its activity. The implications of this technology are vast, with potential applications in everything from crime prevention to personalized healthcare. However, concerns have been raised over the potential for misuse of this technology and the potential invasion of privacy.",
                published_date = "12 Feb, 2021 13:39"
            )
        val mockDomainStoryModel = DomainStoryModel(
            id = "1",
            author = "Ethan Jackson",
            title = "New AI technology can read your mind and predict your future actions",
            content = "In a breakthrough in AI technology, a new program has been developed that can read a person's thoughts and predict their future actions. The program, created by a team of researchers at a leading technology company, uses advanced neural imaging techniques to scan the brain and interpret its activity. The implications of this technology are vast, with potential applications in everything from crime prevention to personalized healthcare. However, concerns have been raised over the potential for misuse of this technology and the potential invasion of privacy.",
            publishedDate = "12 Feb, 2021 13:39"
        )

        coEvery { mockNewsApi.getNewsStory(mockHeadlineId) } throws networkException
        coEvery { mockNewsFeedDao.getHeadlineStory(headline_id = mockHeadlineId) } returns mockStoryEntity
        every { mockDataMapper.mapDomainStoryModel(mockStoryEntity) } returns mockDomainStoryModel
        var result: DomainSealedResponse<DomainStoryModel?>? = null
        mockScope.launch {
            result = newsRepository.getHeadlineStory(id = mockHeadlineId)
        }
        assertTrue(result is DomainSealedResponse.Success)
        assertTrue(result?.data != null && result?.data != null)
    }

    @Test
    fun hasFoundNoOfflineStoryWithNetworkException() = runTest {
        val mockHeadlineId = "1"
        val networkException = mockk<ApiException.NetworkError>(relaxed = true)
        coEvery { mockNewsApi.getNewsStory(mockHeadlineId) } throws networkException
        coEvery { mockNewsFeedDao.getHeadlineStory(headline_id = mockHeadlineId) } returns null
        var result: DomainSealedResponse<DomainStoryModel?>? = null
        mockScope.launch {
            result = newsRepository.getHeadlineStory(id = mockHeadlineId)
        }
        assertTrue(result is DomainSealedResponse.Error)
        assertTrue(result?.error != null && result?.error?.errorCode == ERROR_CODE_NETWORK)
    }
}