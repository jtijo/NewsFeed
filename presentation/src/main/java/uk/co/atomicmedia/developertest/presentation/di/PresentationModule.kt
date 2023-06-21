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

@Module
@InstallIn(SingletonComponent::class)
object PresentationModule {

    @Singleton
    @Provides
    @Named("UseCaseScopeIO")
    fun providesCoroutineScopeIO(): CoroutineScope = useCaseScopeIO

    @Singleton
    @Provides
    @Named("UseCaseScopeMain")
    fun providesCoroutineScopeMain(): CoroutineScope = useCaseScopeMain
}