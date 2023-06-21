package uk.co.atomicmedia.developertest.data.repository

import uk.co.atomicmedia.developertest.data.api.coroutines.CoroutineNewsApi
import uk.co.atomicmedia.developertest.data.api.coroutines.ICoroutineNewsApi

class NewsRepository(
    private val newsApi: ICoroutineNewsApi = CoroutineNewsApi()
) {

}