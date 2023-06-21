package uk.co.atomicmedia.developertest.domain.model.sealed

import uk.co.atomicmedia.developertest.domain.model.DomainErrorResponse

sealed class DomainSealedResponse<T>(
    val data: T? = null,
    val error: DomainErrorResponse? = null
) {
    class Success<T>(data: T) : DomainSealedResponse<T>(data)
    class Error<T>(error: DomainErrorResponse?, data: T? = null) :
        DomainSealedResponse<T>(data, error)
}
