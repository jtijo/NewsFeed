package uk.co.atomicmedia.developertest.presentation.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

object CoroutineScopeUtil {

    val useCaseScopeIO = Dispatchers.IO

    val useCaseScopeMain = CoroutineScope(Dispatchers.Main)
}