package uk.co.atomicmedia.developertest.presentation.features.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import uk.co.atomicmedia.developertest.presentation.utils.SPLASH_SCREEN_DELAY_IN_MILLI_SEC
import uk.co.atomicmedia.developertest.presentation.R

object SplashScreen {
    @Composable
    fun ShowSplashScreen(onNavigateToProductList: () -> Unit) {
        Box(contentAlignment = Alignment.BottomEnd) {
            Column(
                modifier = Modifier
                    .background(color = Color.Black)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp
                )
                LaunchedEffect(Unit)
                {
                    delay(SPLASH_SCREEN_DELAY_IN_MILLI_SEC)
                    onNavigateToProductList.invoke()
                }
            }
            val description = "Atomic Media Test - Tijo Jospeh"
            Text(
                text = description,
                color = Color.White,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

    }
}