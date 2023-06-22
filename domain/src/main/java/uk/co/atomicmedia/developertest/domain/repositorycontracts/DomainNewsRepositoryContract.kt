package uk.co.atomicmedia.developertest.domain.repositorycontracts

import uk.co.atomicmedia.developertest.domain.model.DomainHeadlineList
import uk.co.atomicmedia.developertest.domain.model.DomainStoryModel
import uk.co.atomicmedia.developertest.domain.model.sealed.DomainSealedResponse

interface DomainNewsRepositoryContract {

    suspend fun getHeadLineList(): DomainSealedResponse<DomainHeadlineList>

    suspend fun getHeadlineStory(id: String): DomainSealedResponse<DomainStoryModel?>
}