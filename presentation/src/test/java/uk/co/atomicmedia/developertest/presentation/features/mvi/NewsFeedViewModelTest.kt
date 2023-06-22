package uk.co.atomicmedia.developertest.presentation.features.mvi

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import uk.co.atomicmedia.developertest.presentation.model.PresentationErrorResponse
import uk.co.atomicmedia.developertest.presentation.model.PresentationHeadlineList
import uk.co.atomicmedia.developertest.presentation.model.PresentationHeadlineModel
import uk.co.atomicmedia.developertest.presentation.model.PresentationStoryModel
import uk.co.atomicmedia.developertest.presentation.model.sealed.PresentationSealedResponse

@RunWith(JUnit4::class)
class NewsFeedViewModelTest {

    private lateinit var viewModel: NewsFeedViewModel
    private lateinit var mockNewsFeedModel: NewsFeedModel
    private lateinit var mockScope: TestScope

    @Before
    fun setUp() {
        mockNewsFeedModel = mockk(relaxed = true)
        val dispatcher = UnconfinedTestDispatcher()
        mockScope = TestScope(dispatcher)
        viewModel = NewsFeedViewModel(newsFeedModel = mockNewsFeedModel, useCaseScope = mockScope)
    }

    @Test
    fun getHeadlineListSuccess() = runTest {
        val mockPresentationHeadlineList = PresentationHeadlineList(
            headlineList = listOf(
                PresentationHeadlineModel(
                    "1",
                    "New AI technology can read your mind and predict your future actions",
                    "Ethan Jackson"
                ), PresentationHeadlineModel(
                    "1",
                    "New AI technology can read your mind and predict your future actions",
                    "Ethan Jackson"
                )
            )
        )

        val mockPresentationSealedResponse = PresentationSealedResponse.Success(
            mockPresentationHeadlineList
        )
        coEvery { mockNewsFeedModel.getHeadlineList() } returns mockPresentationSealedResponse

        var initialResults: List<NewsFeedViewModel.CurrentUIState>? = null
        mockScope.launch {
            viewModel.acceptIntention(intention = NewsFeedViewModel.Intention.RequestHeadlineList)
            initialResults = viewModel.newsFeedStateFlow.take(2).toList()
        }.join()
        assertEquals(true, initialResults?.isNotEmpty())
        initialResults?.let { resultList ->
            assertEquals(true, resultList[0] is NewsFeedViewModel.CurrentUIState.Loading)
            assertEquals(
                true,
                resultList[1] is NewsFeedViewModel.CurrentUIState.HeadlineListSuccess
            )
            val resultData = resultList[1] as NewsFeedViewModel.CurrentUIState.HeadlineListSuccess
            assertTrue(resultData.headlineList.isNotEmpty())
        }
    }

    @Test
    fun getHeadlineListError() = runTest {
        val mockPresentationErrorResponse = PresentationErrorResponse(
            errorMessage = "Something went wrong"
        )
        val mockPresentationSealedResponse: PresentationSealedResponse<PresentationHeadlineList> =
            PresentationSealedResponse.Error(
                mockPresentationErrorResponse
            )
        coEvery { mockNewsFeedModel.getHeadlineList() } returns mockPresentationSealedResponse

        var initialResults: List<NewsFeedViewModel.CurrentUIState>? = null
        mockScope.launch {
            viewModel.acceptIntention(intention = NewsFeedViewModel.Intention.RequestHeadlineList)
            initialResults = viewModel.newsFeedStateFlow.take(2).toList()
        }.join()
        assertEquals(true, initialResults?.isNotEmpty())
        initialResults?.let { resultList ->
            assertEquals(true, resultList[0] is NewsFeedViewModel.CurrentUIState.Loading)
            assertEquals(
                true,
                resultList[1] is NewsFeedViewModel.CurrentUIState.Error
            )
            val resultData = resultList[1] as NewsFeedViewModel.CurrentUIState.Error
            assertTrue(resultData.errorMessage != null)
            assertEquals(resultData.errorMessage, mockPresentationErrorResponse.errorMessage)
        }
    }

    @Test
    fun getHeadlineStorySuccess() = runTest {
        val mockHeadlineId = "1"
        val mockPresentationStoryModel = PresentationStoryModel(
            id = "1",
            author = "Ethan Jackson",
            title = "New AI technology can read your mind and predict your future actions",
            content = "In a breakthrough in AI technology, a new program has been developed that can read a person's thoughts and predict their future actions. The program, created by a team of researchers at a leading technology company, uses advanced neural imaging techniques to scan the brain and interpret its activity. The implications of this technology are vast, with potential applications in everything from crime prevention to personalized healthcare. However, concerns have been raised over the potential for misuse of this technology and the potential invasion of privacy.",
            publishedDate = "12 Feb, 2021 13:39"
        )

        val mockPresentationSealedResponse: PresentationSealedResponse<PresentationStoryModel?> =
            PresentationSealedResponse.Success(mockPresentationStoryModel)

        coEvery { mockNewsFeedModel.getHeadlineStory(id = mockHeadlineId) } returns mockPresentationSealedResponse
        var initialResults: List<NewsFeedViewModel.CurrentUIState>? = null
        mockScope.launch {
            viewModel.headlineId = mockHeadlineId
            viewModel.acceptIntention(intention = NewsFeedViewModel.Intention.RequestHeadlineStory)
            initialResults = viewModel.newsFeedStateFlow.take(2).toList()
        }.join()
        assertEquals(true, initialResults?.isNotEmpty())
        initialResults?.let { resultList ->
            assertEquals(true, resultList[0] is NewsFeedViewModel.CurrentUIState.Loading)
            assertEquals(
                true,
                resultList[1] is NewsFeedViewModel.CurrentUIState.HeadlineStorySuccess
            )
            val resultData = resultList[1] as NewsFeedViewModel.CurrentUIState.HeadlineStorySuccess
            assertTrue(resultData.headlineStory != null)
        }
    }

    @Test
    fun getHeadlineStoryError() = runTest {
        val mockHeadlineId = "1"
        val mockPresentationErrorResponse = PresentationErrorResponse(
            errorMessage = "Something went wrong"
        )

        val mockPresentationSealedResponse: PresentationSealedResponse<PresentationStoryModel?> =
            PresentationSealedResponse.Error(mockPresentationErrorResponse)

        coEvery { mockNewsFeedModel.getHeadlineStory(id = mockHeadlineId) } returns mockPresentationSealedResponse
        var initialResults: List<NewsFeedViewModel.CurrentUIState>? = null
        mockScope.launch {
            viewModel.headlineId = mockHeadlineId
            viewModel.acceptIntention(intention = NewsFeedViewModel.Intention.RequestHeadlineStory)
            initialResults = viewModel.newsFeedStateFlow.take(2).toList()
        }.join()
        assertEquals(true, initialResults?.isNotEmpty())
        initialResults?.let { resultList ->
            assertEquals(true, resultList[0] is NewsFeedViewModel.CurrentUIState.Loading)
            assertEquals(
                true,
                resultList[1] is NewsFeedViewModel.CurrentUIState.Error
            )
            val resultData = resultList[1] as NewsFeedViewModel.CurrentUIState.Error
            assertTrue(resultData.errorMessage != null)
            assertEquals(resultData.errorMessage, mockPresentationErrorResponse.errorMessage)
        }
    }

    @Test
    fun reset() = runTest {
        var initialResults: List<NewsFeedViewModel.CurrentUIState>? = null
        mockScope.launch {
            viewModel.acceptIntention(intention = NewsFeedViewModel.Intention.Reset)
            initialResults = viewModel.newsFeedStateFlow.take(1).toList()
        }.join()
        assertEquals(true, initialResults?.isNotEmpty())
        initialResults?.let { resultList ->
            assertEquals(true, resultList[0] is NewsFeedViewModel.CurrentUIState.Loading)
            assertTrue(viewModel.headlineId == null)
        }
    }


}