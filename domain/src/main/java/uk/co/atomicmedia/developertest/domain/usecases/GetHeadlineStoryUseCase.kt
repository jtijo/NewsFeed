package uk.co.atomicmedia.developertest.domain.usecases

import kotlinx.coroutines.withContext
import uk.co.atomicmedia.developertest.domain.model.DomainStoryModel
import uk.co.atomicmedia.developertest.domain.model.sealed.DomainSealedResponse
import uk.co.atomicmedia.developertest.domain.repositorycontracts.DomainNewsRepositoryContract
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetHeadlineStoryUseCase @Inject constructor(
    private val domainNewsRepository: DomainNewsRepositoryContract,
    private val useCaseContextIO: CoroutineContext
) : BaseReturnParameterizedUseCase<String, DomainSealedResponse<DomainStoryModel?>>() {
    override suspend fun execute(params: String): DomainSealedResponse<DomainStoryModel?> {
        return withContext(useCaseContextIO)
        {
            domainNewsRepository.getHeadlineStory(id = params)
        }
    }
}