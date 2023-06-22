package uk.co.atomicmedia.developertest.presentation.features.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.StateFlow
import uk.co.atomicmedia.developertest.presentation.R
import uk.co.atomicmedia.developertest.presentation.features.mvi.BaseIntentionListener
import uk.co.atomicmedia.developertest.presentation.features.mvi.NewsFeedViewModel
import uk.co.atomicmedia.developertest.presentation.model.PresentationHeadlineModel

object HeadlineListScreen {
    @Composable
    fun Initialise(
        currentUIStateFlow: StateFlow<NewsFeedViewModel.CurrentUIState>,
        intentionListener: BaseIntentionListener<NewsFeedViewModel.Intention>,
        onNavigateToHeadlineStory: (String) -> Unit
    ) {
        ListenAppLifeCycle(intentionListener = intentionListener)
        ObserveUIState(
            currentUIStateStateFlow = currentUIStateFlow,
            intentionListener = intentionListener,
            onNavigateToHeadlineStory = onNavigateToHeadlineStory
        )
    }

    @Composable
    private fun ListenAppLifeCycle(intentionListener: BaseIntentionListener<NewsFeedViewModel.Intention>) {
        ComposableLifecycle { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                intentionListener.acceptIntention(
                    intention = NewsFeedViewModel.Intention.RequestHeadlineList
                )
            } else if (event == Lifecycle.Event.ON_DESTROY) {
                intentionListener.acceptIntention(
                    intention = NewsFeedViewModel.Intention.Reset
                )
            }
        }
    }

    @Composable
    private fun ObserveUIState(
        currentUIStateStateFlow: StateFlow<NewsFeedViewModel.CurrentUIState>,
        intentionListener: BaseIntentionListener<NewsFeedViewModel.Intention>,
        onNavigateToHeadlineStory: (String) -> Unit
    ) {
        currentUIStateStateFlow.collectAsState().value.let { currentUIState ->
            when (currentUIState) {
                is NewsFeedViewModel.CurrentUIState.HeadlineListSuccess -> {
                    ShowHeadlineList(
                        headlineList = currentUIState.headlineList,
                        onNavigateToHeadlineStory = onNavigateToHeadlineStory
                    )
                }
                is NewsFeedViewModel.CurrentUIState.Error -> {
                    currentUIState.errorMessage?.let {
                        ShowError(errorMessage = it)
                    }
                }
                is NewsFeedViewModel.CurrentUIState.Loading -> {
                    ShowProgressBar()
                }
                else -> {}
            }
        }
    }

    @Composable
    private fun ColumnScope.ShowTopAppBar() {
        TopAppBar(backgroundColor = Color.Black) {
            Text(
                text = stringResource(id = R.string.headlines),
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 12.dp)
            )
        }
    }

    @Composable
    private fun ShowHeadlineList(
        headlineList: List<PresentationHeadlineModel>,
        onNavigateToHeadlineStory: (String) -> Unit
    ) {
        var gridState = rememberLazyGridState()
        Column(
            modifier = Modifier
                .background(color = Color.LightGray)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            ShowTopAppBar()
            if (headlineList.isEmpty()) {
                ShowNoHeadlinesAvailable()
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    state = gridState
                )
                {
                    items(headlineList.size) {
                        ShowHeadline(
                            headline = headlineList[it],
                            onNavigateToHeadlineStory = onNavigateToHeadlineStory
                        )
                    }
                }
            }
        }

    }

    @Composable
    private fun LazyGridItemScope.ShowHeadline(
        headline: PresentationHeadlineModel,
        onNavigateToHeadlineStory: (String) -> Unit
    ) {
        Column(
            modifier = Modifier
                .background(color = Color.LightGray)
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .wrapContentHeight()
                .clickable {
                    onNavigateToHeadlineStory.invoke(headline.id)
                }
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = headline.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = headline.author,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.height(16.dp))
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = Color.LightGray)
            )
        }
    }

    @Composable
    private fun ShowNoHeadlinesAvailable() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.no_headlines_available), color = Color.Black
            )
        }
    }

    @Composable
    private fun ShowError(errorMessage: String) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            ShowTopAppBar()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.error),
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = errorMessage,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Left
                )
            }
        }

    }

    @Composable
    private fun ShowProgressBar() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            CircularProgressIndicator(
                modifier = Modifier.size(32.dp),
                color = colorResource(id = R.color.black),
                strokeWidth = 4.dp
            )
        }
    }

    @Composable
    fun ComposableLifecycle(
        lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
        onEvent: (LifecycleOwner, Lifecycle.Event) -> Unit
    ) {
        DisposableEffect(lifeCycleOwner) {
            val observer = LifecycleEventObserver { source, event ->
                onEvent(source, event)
            }
            lifeCycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifeCycleOwner.lifecycle.removeObserver(observer)
            }
        }
    }
}