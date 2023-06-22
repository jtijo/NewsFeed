package uk.co.atomicmedia.developertest.presentation.features.compose


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.co.atomicmedia.developertest.presentation.features.mvi.NewsFeedViewModel
import uk.co.atomicmedia.developertest.presentation.utils.*
import java.lang.ref.WeakReference

object NavigationControllerScreen {
    @Composable
    fun initialise(viewModel: NewsFeedViewModel) {
        val navController = rememberNavController()
        val navControllerWeakReference = WeakReference(navController)
        navControllerWeakReference.get()?.let { navigationController ->
            NavHost(
                navController = navigationController,
                startDestination = NAV_SPLASH_SCREEN
            )
            {
                composable(route = NAV_SPLASH_SCREEN)
                {
                    launchSplashScreen(navigationController = navigationController)
                }
                composable(route = NAV_HEADLINE_LIST)
                {
                    launchHeadlineListScreen(
                        viewModel = viewModel,
                        navigationController = navigationController
                    )
                }
                composable(route = NAV_ROUTE_HEADLINE_STORY)
                { backStackEntry ->
                    viewModel.headlineId =
                        backStackEntry.arguments?.getString(STORY_ID)
                    launchHeadlineStoryScreen(
                        viewModel = viewModel,
                        navigationController = navigationController
                    )
                }
            }
        }
    }


    @Composable
    private fun launchSplashScreen(navigationController: NavHostController) {
        SplashScreen.ShowSplashScreen {
            navigationController.navigate(route = NAV_HEADLINE_LIST)
            {
                popUpTo(NAV_SPLASH_SCREEN)
                {
                    inclusive = true
                }
            }
        }
    }

    @Composable
    private fun launchHeadlineListScreen(
        viewModel: NewsFeedViewModel,
        navigationController: NavHostController
    ) {
        HeadlineListScreen.Initialise(
            currentUIStateFlow = viewModel.newsFeedStateFlow,
            intentionListener = viewModel,
            onNavigateToHeadlineStory = { headlineId ->
                navigationController.navigate("$NAV_HEADLINE_STORY/$headlineId")
            }
        )
    }

    @Composable
    private fun launchHeadlineStoryScreen(
        viewModel: NewsFeedViewModel,
        navigationController: NavHostController
    ) {
        HeadlineStoryScreen.Initialise(
            currentUIStateFlow = viewModel.newsFeedStateFlow,
            intentionListener = viewModel,
            onBackPressed = {
                navigationController.popBackStack()
            }
        )
    }
}