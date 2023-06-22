package uk.co.atomicmedia.developertest.presentation.features

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import dagger.hilt.android.AndroidEntryPoint
import uk.co.atomicmedia.developertest.presentation.features.compose.NavigationControllerScreen
import uk.co.atomicmedia.developertest.presentation.features.mvi.NewsFeedViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: NewsFeedViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                NavigationControllerScreen.Initialise(viewModel = viewModel)
            }
        }
    }
}