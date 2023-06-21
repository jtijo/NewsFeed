package uk.co.atomicmedia.developertest.data.repository

import uk.co.atomicmedia.developertest.data.api.coroutines.CoroutineNewsApi
import uk.co.atomicmedia.developertest.data.api.coroutines.ICoroutineNewsApi
import uk.co.atomicmedia.developertest.data.boundary.DataMapper
import uk.co.atomicmedia.developertest.data.database.NewsFeedDao

class NewsRepository(
    private val newsApi: ICoroutineNewsApi,
    private val newsFeedDao: NewsFeedDao,
    private val mapper: DataMapper
) {

}