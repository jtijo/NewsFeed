package uk.co.atomicmedia.developertest.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uk.co.atomicmedia.developertest.data.api.coroutines.CoroutineNewsApi
import uk.co.atomicmedia.developertest.data.api.coroutines.ICoroutineNewsApi
import uk.co.atomicmedia.developertest.data.boundary.DataMapper
import uk.co.atomicmedia.developertest.data.database.NewsFeedDao
import uk.co.atomicmedia.developertest.data.database.NewsFeedDatabase
import uk.co.atomicmedia.developertest.data.repository.NewsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun providesNewsFeedDatabase(@ApplicationContext applicationContext: Context): NewsFeedDatabase {
        return NewsFeedDatabase.getNewsFeedDatabase(context = applicationContext)!!
    }

    @Singleton
    @Provides
    fun providesNewsFeedDao(database: NewsFeedDatabase): NewsFeedDao {
        return database.getNewsFeedDao()
    }

    @Singleton
    @Provides
    fun providesNewsRepository(
        newsFeedDao: NewsFeedDao,
        coroutineNewsApi: CoroutineNewsApi,
        dataMapper: DataMapper
    ): NewsRepository {
        return NewsRepository(
            newsApi = coroutineNewsApi as ICoroutineNewsApi,
            newsFeedDao = newsFeedDao,
            mapper = dataMapper
        )
    }
}