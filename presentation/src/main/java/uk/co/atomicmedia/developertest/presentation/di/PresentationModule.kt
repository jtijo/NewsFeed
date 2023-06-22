package uk.co.atomicmedia.developertest.presentation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import uk.co.atomicmedia.developertest.presentation.utils.CoroutineScopeUtil.useCaseScopeIO
import uk.co.atomicmedia.developertest.presentation.utils.CoroutineScopeUtil.useCaseScopeMain
import javax.inject.Named
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
object PresentationModule {

    @Singleton
    @Provides
    fun providesCoroutineContext(): CoroutineContext = useCaseScopeIO

    @Singleton
    @Provides
    fun providesCoroutineScope(): CoroutineScope = useCaseScopeMain
}