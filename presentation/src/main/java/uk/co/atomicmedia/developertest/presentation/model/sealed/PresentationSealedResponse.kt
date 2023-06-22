package uk.co.atomicmedia.developertest.presentation.model.sealed

import uk.co.atomicmedia.developertest.presentation.model.PresentationErrorResponse

sealed class PresentationSealedResponse<T>(
    val data: T? = null,
    val error: PresentationErrorResponse? = null
) {
    class Success<T>(data: T) : PresentationSealedResponse<T>(data)
    class Error<T>(error: PresentationErrorResponse?, data: T? = null) :
        PresentationSealedResponse<T>(data, error)
}
