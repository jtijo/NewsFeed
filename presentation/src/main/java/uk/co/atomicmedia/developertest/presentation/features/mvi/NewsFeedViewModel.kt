package uk.co.atomicmedia.developertest.presentation.features.mvi

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.co.atomicmedia.developertest.presentation.model.PresentationErrorResponse
import uk.co.atomicmedia.developertest.presentation.model.PresentationHeadlineList
import uk.co.atomicmedia.developertest.presentation.model.PresentationHeadlineModel
import uk.co.atomicmedia.developertest.presentation.model.PresentationStoryModel
import uk.co.atomicmedia.developertest.presentation.model.sealed.PresentationSealedResponse
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    private val newsFeedModel: NewsFeedModel,
    private val useCaseScope: CoroutineScope
) : ViewModel(), BaseIntentionListener<NewsFeedViewModel.Intention> {

    var headlineId: String? = null

    private val _newsFeedStateFlow = MutableStateFlow<CurrentUIState>(CurrentUIState.Loading)
    val newsFeedStateFlow: StateFlow<CurrentUIState> = _newsFeedStateFlow

    sealed class CurrentUIState {
        object Loading : CurrentUIState()
        data class HeadlineListSuccess(
            val headlineList: List<PresentationHeadlineModel>
        ) : CurrentUIState()

        data class HeadlineStorySuccess(
            val headlineStory: PresentationStoryModel?
        ) : CurrentUIState()

        data class Error(val errorMessage: String?) : CurrentUIState()
    }

    sealed class Intention {
        object RequestHeadlineList : Intention()

        object RequestHeadlineStory : Intention()

        object Reset : Intention()
    }

    override fun acceptIntention(intention: Intention) {
        when (intention) {
            is Intention.RequestHeadlineList -> {
                triggerGetHeadlineList()
            }

            is Intention.RequestHeadlineStory -> {
                triggerGetHeadlineStory()
            }
            is Intention.Reset -> {

            }
        }
    }

    private fun triggerGetHeadlineList() {
        useCaseScope.launch {
            when (val response = newsFeedModel.getHeadlineList()) {
                is PresentationSealedResponse.Success -> {
                    _newsFeedStateFlow.emit(
                        value = CurrentUIState.HeadlineListSuccess(
                            headlineList = response.data?.headlineList ?: emptyList()
                        )
                    )
                }

                is PresentationSealedResponse.Error -> {
                    _newsFeedStateFlow.emit(value = CurrentUIState.Error(
                        response.error?.let {
                            it.errorMessage
                        }
                    ))
                }
            }
        }
    }

    private fun triggerGetHeadlineStory() {
        headlineId?.let { headlineId ->
            useCaseScope.launch {
                when (val response = newsFeedModel.getHeadlineStory(headlineId)) {
                    is PresentationSealedResponse.Success -> {
                        _newsFeedStateFlow.emit(
                            value = CurrentUIState.HeadlineStorySuccess(
                                response.data
                            )
                        )
                    }

                    is PresentationSealedResponse.Error -> {
                        _newsFeedStateFlow.emit(value = CurrentUIState.Error(
                            response.error?.let {
                                it.errorMessage
                            }
                        ))
                    }
                }
            }
        }
    }

    private fun reset() {

    }
}