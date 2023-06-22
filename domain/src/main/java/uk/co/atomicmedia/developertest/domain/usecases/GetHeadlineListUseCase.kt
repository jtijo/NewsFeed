package uk.co.atomicmedia.developertest.domain.usecases

import kotlinx.coroutines.withContext
import uk.co.atomicmedia.developertest.domain.model.DomainHeadlineList
import uk.co.atomicmedia.developertest.domain.model.sealed.DomainSealedResponse
import uk.co.atomicmedia.developertest.domain.repositorycontracts.DomainNewsRepositoryContract
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetHeadlineListUseCase @Inject constructor(
    private val domainNewsRepository: DomainNewsRepositoryContract,
    private val useCaseContextIO: CoroutineContext
) : BaseReturnUseCase<DomainSealedResponse<DomainHeadlineList>>() {
    override suspend fun execute(): DomainSealedResponse<DomainHeadlineList> {
        return withContext(useCaseContextIO)
        {
            domainNewsRepository.getHeadLineList()
        }
    }
}