package uk.co.atomicmedia.developertest.presentation.features.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
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
import uk.co.atomicmedia.developertest.presentation.model.PresentationStoryModel

object HeadlineStoryScreen {
    @Composable
    fun Initialise(
        currentUIStateFlow: StateFlow<NewsFeedViewModel.CurrentUIState>,
        intentionListener: BaseIntentionListener<NewsFeedViewModel.Intention>,
        onBackPressed: () -> Unit
    ) {
        ShowProgressBar()
        ListenAppLifeCycle(intentionListener = intentionListener)
        ObserveUIState(
            currentUIStateStateFlow = currentUIStateFlow,
            intentionListener = intentionListener,
            onBackPressed = onBackPressed
        )
    }

    @Composable
    private fun ListenAppLifeCycle(intentionListener: BaseIntentionListener<NewsFeedViewModel.Intention>) {
        ComposableLifecycle { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                intentionListener.acceptIntention(
                    intention = NewsFeedViewModel.Intention.RequestHeadlineStory
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
        onBackPressed: () -> Unit
    ) {
        currentUIStateStateFlow.collectAsState().value.let { currentUIState ->
            when (currentUIState) {
                is NewsFeedViewModel.CurrentUIState.HeadlineStorySuccess -> {
                    SetUpHeadlineStoryUI(
                        headlineStory = currentUIState.headlineStory,
                        onBackPressed = onBackPressed
                    )
                }
                is NewsFeedViewModel.CurrentUIState.Error -> {
                    currentUIState.errorMessage?.let {
                        ShowError(errorMessage = it, onBackPressed = onBackPressed)
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
    private fun SetUpHeadlineStoryUI(
        headlineStory: PresentationStoryModel?,
        onBackPressed: () -> Unit
    ) {
        Column(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxWidth()
                .fillMaxHeight()
        )
        {
            ShowTopAppBar(onBackPressed = onBackPressed)
            headlineStory?.let {
                ShowHeadlineStory(headlineStory = it)
            } ?: run {
                ShowNoHeadlinesStoryAvailable()
            }
        }
    }

    @Composable
    private fun ShowHeadlineStory(headlineStory: PresentationStoryModel) {
        val scrollState = rememberScrollState()
        Column(
            Modifier
                .background(color = Color.White)
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(state = scrollState)
                .padding(16.dp)
        ) {
            Text(
                text = headlineStory.title,
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Text(
                    text = headlineStory.author,
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = headlineStory.publishedDate,
                    fontSize = 14.sp,
                    color = Color.Red,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Right,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = headlineStory.content,
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 24.sp
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun ShowTopAppBar(onBackPressed: () -> Unit) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.headline_story),
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 12.dp)
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    onBackPressed.invoke()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_to_headline_list),
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.Black)
        )
    }

    @Composable
    private fun ShowNoHeadlinesStoryAvailable() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.no_headline_story_available),
                color = Color.Black
            )
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

    @Composable
    private fun ShowError(errorMessage: String, onBackPressed: () -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            ShowTopAppBar(onBackPressed = onBackPressed)
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
}