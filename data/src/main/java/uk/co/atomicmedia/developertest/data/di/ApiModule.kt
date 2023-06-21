package uk.co.atomicmedia.developertest.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uk.co.atomicmedia.developertest.data.api.coroutines.CoroutineNewsApi
import uk.co.atomicmedia.developertest.data.api.coroutines.ICoroutineNewsApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ApiModule {
    @Singleton
    @Binds
    abstract fun providesCoroutineNewsApi(coroutineNewsApi: CoroutineNewsApi): ICoroutineNewsApi
}